package com.app.project10.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.single_game.SingleGameRepository
import kotlinx.coroutines.flow.MutableStateFlow

sealed interface GameScreenState {
    data class DisplayingGame(val game: Game) : GameScreenState
    object Loading : GameScreenState
    data class DisplayingError(val error: String) : GameScreenState
}

class GameViewModel(private val gameRepository: SingleGameRepository) : ViewModel() {

    private val gameIdFlow = MutableStateFlow<Int?>(null)

    val gameState = flowUiState(
        scope = viewModelScope,
        initialInput = gameIdFlow.value,
        builder = {
            initial { GameScreenState.Loading }

            debounce(300)

            fetch { gameId ->
                val game = gameRepository.getGame(gameId ?: -1)
                GameScreenState.DisplayingGame(game.toGameResponse())
            }

            onError { e ->
                GameScreenState.DisplayingError(e.message ?: "Unknown error")
            }
        }
    )

    val state = gameState.state

    fun setGameId(id: Int) {
        gameState.update(id)
    }


    fun onRefresh() {
        gameState.refresh()
    }
}
