package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.utils.FlowState
import com.app.project10.utils.TimeUtils.todayDate
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

sealed interface MainScreenState {
    data class DisplayingGames(val games: List<Game>, val input: String) : MainScreenState
    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val gamesState = FlowState<String, MainScreenState>(
        scope = viewModelScope,
        initialSourceValue = todayDate,
        initialValue = MainScreenState.Loading,
        fetcher = { date, _ -> // We ignore the second list of other sources for now
            flow {
                emit(MainScreenState.Loading)
                try {
                    val games = gamesRepository.getGames(date)
                    emit(MainScreenState.DisplayingGames(games, date))
                } catch (error: Exception) {
                    emit(MainScreenState.DisplayingError(error.message ?: "Unknown error"))
                }
            }
        },
        onError = { error ->
            MainScreenState.DisplayingError(error.message ?: "Unknown error")
        }
    )

    val state = gamesState.state

    fun onDateChanged(newDate: LocalDate) {
        gamesState.onInputChange(newDate.toString())
    }

    fun onRefresh() {
        gamesState.refresh()
    }
}

