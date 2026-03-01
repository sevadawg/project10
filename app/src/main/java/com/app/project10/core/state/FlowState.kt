package com.app.project10.core.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.cancellation.CancellationException

private sealed interface FlowTrigger<out S> {
    data class Input<S>(val value: S) : FlowTrigger<S>
    data object Refresh : FlowTrigger<Nothing>
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <S, R> flowUiState(
    scope: CoroutineScope,
    initialInput: S,
    builder: FlowUiStateBuilder<S, R>.() -> Unit
): FlowUiStateController<S, R> {

    val flowBuilder = FlowUiStateBuilder<S, R>().apply(builder)

    val input = MutableStateFlow(initialInput)
    val refresh = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state: StateFlow<R> = merge(
        input.map<S, FlowTrigger<S>> { FlowTrigger.Input(it) },
        refresh.map<Unit, FlowTrigger<S>> { FlowTrigger.Refresh }
    )
        .flatMapLatest { trigger ->
            flow {
                try {
                    when (trigger) {
                        is FlowTrigger.Input -> {
                            emit(flowBuilder.loadingState)
                            emit(flowBuilder.fetcher(trigger.value))
                        }

                        FlowTrigger.Refresh -> {
                            emit(flowBuilder.loadingState)
                            emit(flowBuilder.fetcher(input.value))
                        }
                    }
                } catch (e: Throwable) {
                    if (e is CancellationException) throw e
                    emit(flowBuilder.errorMapper(e))
                }
            }
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = flowBuilder.initialState
        )

    return FlowUiStateController(input, refresh, state)
}
