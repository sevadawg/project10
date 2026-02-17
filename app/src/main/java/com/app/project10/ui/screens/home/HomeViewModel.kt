package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.core.utils.DateTimeUtils.todayDateString
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.games.GamesRepository
import timber.log.Timber
import java.time.LocalDate

sealed interface HomeScreenState {
    data class DisplayingGames(val games: List<Game>, val input: String) : HomeScreenState
    data object Loading : HomeScreenState
    data class DisplayingError(val error: String) : HomeScreenState
}

class HomeViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val gamesState = flowUiState(
        scope = viewModelScope,
        initialInput = todayDateString
    ) {
        initial { HomeScreenState.Loading }

        fetch { date ->
            val games = gamesRepository.getGames(date)
            HomeScreenState.DisplayingGames(games, date)
        }

        onError { e ->
            Timber.e(e)
            HomeScreenState.DisplayingError(e.message ?: "Unknown error")
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


