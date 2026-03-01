package com.app.project10.presentation.screens.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.data.remote.dto.standings.Standing
import com.app.project10.domain.repository.StandingsRepository
import timber.log.Timber
import java.time.LocalDate

sealed interface StandingsScreenState {
    data class DisplayingStandings(val standings: List<Standing>, val season: Int) :
        StandingsScreenState

    data object Loading : StandingsScreenState
    data class DisplayingError(val error: String) : StandingsScreenState
}

class StandingsViewModel(
    private val standingsRepository: StandingsRepository
) : ViewModel() {

    private val standingsState = flowUiState(
        scope = viewModelScope,
        initialInput = LocalDate.now().year
    ) {
        initial { StandingsScreenState.Loading }
        loading { StandingsScreenState.Loading }

        fetch { season ->
            val standings = standingsRepository.getStandings(season)
            StandingsScreenState.DisplayingStandings(standings = standings, season = season)
        }

        onError { e ->
            Timber.e(e)
            StandingsScreenState.DisplayingError(e.message ?: "Unknown error")
        }
    }

    val state = standingsState.state

    fun onRefresh() {
        standingsState.refresh()
    }
}

