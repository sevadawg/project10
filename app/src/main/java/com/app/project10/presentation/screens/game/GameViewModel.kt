package com.app.project10.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.data.remote.dto.game.Game
import com.app.project10.domain.repository.SingleGameRepository
import timber.log.Timber

sealed interface GameScreenState {
    data class DisplayingGame(val game: Game) : GameScreenState
    object Loading : GameScreenState
    data class DisplayingError(val error: String) : GameScreenState
}

class GameViewModel(private val gameRepository: SingleGameRepository) : ViewModel() {

    private val gameState = flowUiState<Int?, GameScreenState>(
        scope = viewModelScope,
        initialInput = null,
        builder = {
            initial { GameScreenState.Loading }

            fetch { gameId ->
                val safeGameId = gameId
                    ?.takeIf { it > 0 }
                    ?: return@fetch GameScreenState.Loading

                val game = gameRepository.getGame(safeGameId)

                GameScreenState.DisplayingGame(game.toGameResponse())
            }

            onError { e ->
                Timber.e(e)
                GameScreenState.DisplayingError(e.message ?: "Unknown error")
            }
        }
    )

    val state = gameState.state

    fun setGameId(id: Int) {
        if (id <= 0) return
        gameState.update(id)
    }

    fun onRefresh() {
        if ((gameState.currentInput ?: -1) <= 0) return
        gameState.refresh()
    }
}


