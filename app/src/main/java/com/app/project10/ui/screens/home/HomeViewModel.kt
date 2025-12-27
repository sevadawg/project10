package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.dto.Game
import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.utils.TimeUtils.todayDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    data class DisplayingGames(val games: List<Game>, val input: String) :
        MainScreenState

    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val input = MutableStateFlow(todayDate)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val games = input.flatMapLatest { date ->
        flow {
            emit(gamesRepository.getGames(date))
        }
    }

    val state = combine(
        games,
        input
    ) { games, input ->

        MainScreenState.DisplayingGames(games, input) as MainScreenState
    }.catch {
        emit(MainScreenState.DisplayingError(it.message ?: "Unknown error"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 1000,
            replayExpirationMillis = 5000
        ),
        initialValue = MainScreenState.Loading
    )

    fun onDateChanged(newDate: LocalDate) {
        val newDate = newDate.toString()
        input.update { newDate }
    }

    fun onRefresh() {
        input.update {
            todayDate
        }
    }
}