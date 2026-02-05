package com.app.project10.data.repository.user_preferences

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.project10.network.base.CryptoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(
    context: Context,
    scope: CoroutineScope
) {

    private val dataStore = context.applicationContext.dataStore

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val AUTH_TOKEN_IV = stringPreferencesKey("auth_token_iv")
    }

    private val _token = MutableStateFlow<String?>(null)
    val authTokenFlow: StateFlow<String?> = _token

    init {
        scope.launch {
            dataStore.data
                .catch { e ->
                    if (e is CancellationException) throw e
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
                        null
                    }
                }
                .collect { _token.value = it }
        }
    }

    fun getToken(): String? = _token.value

    suspend fun saveAuthToken(token: String) {
        val (iv, encrypted) = CryptoManager.encrypt(token)

        dataStore.edit {
            it[PreferencesKeys.AUTH_TOKEN] =
                Base64.encodeToString(encrypted, Base64.NO_WRAP)
            it[PreferencesKeys.AUTH_TOKEN_IV] =
                Base64.encodeToString(iv, Base64.NO_WRAP)
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit {
            it.remove(PreferencesKeys.AUTH_TOKEN)
            it.remove(PreferencesKeys.AUTH_TOKEN_IV)
        }
        _token.value = null
    }
}
