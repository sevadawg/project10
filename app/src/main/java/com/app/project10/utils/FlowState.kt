package com.app.project10.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * A reusable class that encapsulates state management logic for flows.
 *
 * @param S The type of the updatable source data (e.g., String for a date or search query).
 * @param R The type of the final state emitted (e.g., a sealed interface like MainScreenState).
 * @param scope The CoroutineScope in which to launch the state flow.
 * @param initialSourceValue The starting value for the internal, updatable data source.
 * @param otherSources A list of *other*, non-updatable flows that can also trigger a refresh.
 * @param initialValue The initial value for the resulting StateFlow (e.g., a Loading state).
 * @param fetcher A suspend function that takes the latest values from all sources and
 *                produces a Flow of the final state [R].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FlowState<S, R>(
    scope: CoroutineScope,
    initialSourceValue: S,
    otherSources: List<Flow<*>> = emptyList(), // Optional: for other triggers
    initialValue: R,
    private val fetcher: suspend (S, List<*>) -> Flow<R>,
    private val onError: (Throwable) -> R
) {
    // 1. This is the new, internally managed StateFlow for the primary input.
    private val updatableSource = MutableStateFlow(initialSourceValue)

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit) // Emit immediately to trigger the first fetch
    }

    val state: StateFlow<R> = combine(
        // 2. Combines the new internal source with other sources and the refresh trigger.
        listOf(updatableSource) + otherSources + listOf(refreshTrigger)
    ) { values ->
        val sourceValue = values.first() as S
        val otherValues = values.drop(1).dropLast(1)
        sourceValue to otherValues
    }.flatMapLatest { (sourceValue, otherValues) ->
        // 3. Call the fetcher with the extracted values.
        fetcher(sourceValue, otherValues)
            .catch { error ->
                emit(onError(error))
            }
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initialValue
    )

    /**
     * Public function to update the internal data source, which will trigger a refetch.
     * This replaces the need for onDateChanged in the ViewModel.
     */
    fun onInputChange(newValue: S) {
        updatableSource.update { newValue }
    }

    /**
     * Public function to trigger a manual refresh of the data.
     */
    fun refresh() {
        refreshTrigger.tryEmit(Unit)
    }
}