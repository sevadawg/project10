package com.app.project10.core.state

class FlowUiStateBuilder<S, R> {
    private var _fetcher: (suspend (S) -> R)? = null
    private var _errorMapper: ((Throwable) -> R)? = null
    private var _initialState: R? = null

    val fetcher: suspend (S) -> R
        get() = requireNotNull(_fetcher) { "${javaClass.name}\nfetch { } must be provided" }

    val errorMapper: (Throwable) -> R
        get() = requireNotNull(_errorMapper) { "${javaClass.name}\nonError { } must be provided" }

    val initialState: R
        get() = requireNotNull(_initialState) { "${javaClass.name}\ninitial { } must be provided" }

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

