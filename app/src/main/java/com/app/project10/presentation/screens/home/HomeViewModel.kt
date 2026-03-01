package com.app.project10.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.core.utils.DateTimeUtils.todayDateString
import com.app.project10.data.remote.dto.game.Game
import com.app.project10.domain.repository.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            withContext(Dispatchers.IO) {
                val games = gamesRepository.getGames(date)
                HomeScreenState.DisplayingGames(games, date)
            }
        }

        loading {
            HomeScreenState.Loading
        }

        onError { e ->
            Timber.e(e)
            HomeScreenState.DisplayingError(e.message ?: "Unknown error")
        }
    }

    val state = gamesState.state

    fun onDateChanged(newDate: LocalDate) {
        val newInput = newDate.toString()
        if (gamesState.currentInput == newInput) {
            gamesState.refresh()
            return
        }
        gamesState.update(newInput)
    }

    fun onRefresh() {
        gamesState.refresh()
    }
}


