package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.core.utils.TimeUtils.todayDate
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.games.GamesRepository
import timber.log.Timber
import java.time.LocalDate

sealed interface MainScreenState {
    data class DisplayingGames(val games: List<Game>, val input: String) : MainScreenState
    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val gamesState = flowUiState(
        scope = viewModelScope,
        initialInput = todayDate
    ) {
        initial { MainScreenState.Loading }

        debounce(300)

        fetch { date ->
            val games = gamesRepository.getGames(date)
            MainScreenState.DisplayingGames(games, date)
        }

        onError { e ->
            Timber.e(e)
            MainScreenState.DisplayingError(e.message ?: "Unknown error")
        }
    }

    val state = gamesState.state

    fun onDateChanged(newDate: LocalDate) {
        gamesState.update(newDate.toString())
    }

    fun onRefresh() {
        gamesState.refresh()
    }
}

