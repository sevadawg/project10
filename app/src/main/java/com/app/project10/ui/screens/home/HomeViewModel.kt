package com.app.project10.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowState
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.utils.TimeUtils.todayDate
import java.time.LocalDate

sealed interface MainScreenState {
    data class DisplayingGames(val games: List<Game>, val input: String) : MainScreenState
    object Loading : MainScreenState
    data class DisplayingError(val error: String) : MainScreenState
}

class MainScreenViewModel(private val gamesRepository: GamesRepository) : ViewModel() {

    private val gamesState = flowState(
        scope = viewModelScope,
        initialInput = todayDate
    ) {
        initial { MainScreenState.Loading }

        debounce(300)

        fetch { date ->
            val games = gamesRepository.getGames(date)
            MainScreenState.DisplayingGames(games, date)
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

