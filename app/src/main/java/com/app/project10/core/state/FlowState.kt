package com.app.project10.core.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
fun <S, R> flowUiState(
    scope: CoroutineScope,
    initialInput: S,
    builder: FlowUiStateBuilder<S, R>.() -> Unit
): FlowUiStateController<S, R> {

    val flowBuilder = FlowUiStateBuilder<S, R>().apply(builder)

    val input = MutableStateFlow(initialInput)
    val refresh = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val state: StateFlow<R> =
        merge(input.map { }, refresh)
            .onStart { emit(Unit) }
            .flatMapLatest {
                input
                    .let { if (flowBuilder.debounceMs > 0) it.debounce(flowBuilder.debounceMs) else it }
                    .mapLatest { value ->
                        try {
                            flowBuilder.fetcher(value)
                        } catch (e: Throwable) {
                            if (e is CancellationException) throw e
                            flowBuilder.errorMapper(e)
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