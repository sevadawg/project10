package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.models.GamesResponse
import com.app.project10.data.repository.TodayGamesRepository
import com.app.project10.utils.TimeUtils.todayDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform

sealed interface MainScreenState {
    data class DisplayingGames(val todayGames: List<GamesResponse>) : MainScreenState
    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val todayGamesRepository: TodayGamesRepository) : ViewModel() {

    private val games = todayGamesRepository.getTodayGames(todayDate)

    val state: StateFlow<MainScreenState> = todayGamesRepository
        .getTodayGames(todayDate)
        .transform<List<GamesResponse>, MainScreenState> { gamesList ->
            emit(MainScreenState.DisplayingGames(gamesList))
        }
        .catch { e ->
            emit(MainScreenState.DisplayingError(e.localizedMessage ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 1000,
                replayExpirationMillis = 5000
            ),
            initialValue = MainScreenState.Loading
        )
}