package com.app.project10.data.local.preferences

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.project10.data.local.security.CryptoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(
    private val context: Context,
    appScope: CoroutineScope
) {

    private val dataStore = context.applicationContext.dataStore
    private val authTokenState = MutableStateFlow<String?>(null)

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val AUTH_TOKEN_IV = stringPreferencesKey("auth_token_iv")
    }

    val authTokenFlow: Flow<String?> = dataStore.data
        .catch { e ->
            if (e is CancellationException) throw e
            Timber.e(e, "Error reading DataStore preferences")
            emit(emptyPreferences())
        }
        .map { prefs ->
            val enc = prefs[PreferencesKeys.AUTH_TOKEN]
            val iv = prefs[PreferencesKeys.AUTH_TOKEN_IV]

            if (enc == null || iv == null) return@map null

            try {
                CryptoManager.decrypt(
                    Base64.decode(iv, Base64.DEFAULT),
                    Base64.decode(enc, Base64.DEFAULT)
                )
            } catch (e: Exception) {
                Timber.e(e, "Error decrypting JWT token")
                null
            }
        }

    init {
        appScope.launch {
            authTokenFlow.collect { token ->
                authTokenState.value = token
            }
        }
    }

    val currentToken: String?
        get() = authTokenState.value

    suspend fun getToken(): String? {
        return authTokenFlow.firstOrNull()
    }

    suspend fun saveAuthToken(token: String) {
        val (iv, encrypted) = CryptoManager.encrypt(token)

        authTokenState.value = token
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.AUTH_TOKEN] = Base64.encodeToString(encrypted, Base64.NO_WRAP)
            prefs[PreferencesKeys.AUTH_TOKEN_IV] = Base64.encodeToString(iv, Base64.NO_WRAP)
        }
    }

    suspend fun clearAuthToken() {
        authTokenState.value = null
        dataStore.edit { prefs ->
            prefs.remove(PreferencesKeys.AUTH_TOKEN)
            prefs.remove(PreferencesKeys.AUTH_TOKEN_IV)
        }
    }
}


