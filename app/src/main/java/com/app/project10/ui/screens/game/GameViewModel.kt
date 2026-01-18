package com.app.project10.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.dto.game.Game
import com.app.project10.data.repository.single_game.SingleGameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

sealed interface GameScreenState {
    data class DisplayingGame(val game: Game) : GameScreenState
    object Loading : GameScreenState
    data class DisplayingError(val error: String) : GameScreenState
}

class GameViewModel(private val gameRepository: SingleGameRepository) : ViewModel() {

    private val gameIdFlow = MutableStateFlow<Int?>(null)
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = combine(
        gameIdFlow.filterNotNull(), // Don't start until we have an ID
        refreshTrigger
    ) { id, _ ->
        id
    }.flatMapLatest { id ->
        flow {
            emit(GameScreenState.Loading)
            val game = gameRepository.getGame(id)
            emit(GameScreenState.DisplayingGame(game))
        }
    }.catch {
        emit(GameScreenState.DisplayingError(it.message ?: "Unknown error"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameScreenState.Loading
    )

    fun setGameId(gameId: Int) {
        // Set the ID and trigger the initial load
        if (gameIdFlow.value == null) {
            gameIdFlow.value = gameId
            onRefresh()
        }
    }

    fun onRefresh() {
        refreshTrigger.tryEmit(Unit)
    }
}
