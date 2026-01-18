package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.dto.Game
import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.utils.TimeUtils.todayDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate

sealed interface MainScreenState {
    data class DisplayingGames(val games: List<Game>, val input: String) : MainScreenState
    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val selectedDate = MutableStateFlow(todayDate)

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = combine(
        selectedDate,
        refreshTrigger
    ) { date, _ ->
        date
    }.flatMapLatest { date ->
        flow {
            emit(MainScreenState.Loading)
            val games = gamesRepository.getGames(date)
            emit(MainScreenState.DisplayingGames(games, date))
        }.catch { e ->
            emit(MainScreenState.DisplayingError(e.message ?: "Unknown error"))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 1000,
            replayExpirationMillis = 5000
        ),
        initialValue = MainScreenState.Loading
    )

    fun onDateChanged(newDate: LocalDate) {
        selectedDate.update { newDate.toString() }
    }

    fun onRefresh() {
        refreshTrigger.tryEmit(Unit)
    }
}