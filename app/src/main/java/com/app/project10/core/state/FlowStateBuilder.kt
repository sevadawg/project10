package com.app.project10.core.state

class FlowStateBuilder<S, R> {
    internal var debounceMs: Long = 0

    private var _fetcher: (suspend (S) -> R)? = null
    private var _errorMapper: ((Throwable) -> R)? = null
    private var _initialState: R? = null

    val fetcher: suspend (S) -> R
        get() = requireNotNull(_fetcher) { "fetch { } must be provided" }

    val errorMapper: (Throwable) -> R
        get() = requireNotNull(_errorMapper) { "onError { } must be provided" }

    val initialState: R
        get() = requireNotNull(_initialState) { "initial { } must be provided" }

    fun debounce(ms: Long) {
        debounceMs = ms
    }

    fun fetch(block: suspend (S) -> R) {
        _fetcher = block
    }

    fun onError(block: (Throwable) -> R) {
        _errorMapper = block
    }

    fun initial(block: () -> R) {
        _initialState = block()
    }
}