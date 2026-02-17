package com.app.project10.core.state

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FlowUiStateController<S, R>(
    private val input: MutableStateFlow<S>,
    private val refresh: MutableSharedFlow<Unit>,
    val state: StateFlow<R>
) {
    val currentInput: S
        get() = input.value

    fun update(value: S) {
        input.value = value
    }

    fun refresh(): Boolean {
        return refresh.tryEmit(Unit)
    }
}
