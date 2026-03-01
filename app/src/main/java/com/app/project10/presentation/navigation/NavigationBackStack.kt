package com.app.project10.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.app.project10.R
import com.app.project10.data.remote.dto.game.Game

sealed interface TopLevelRoute {
    val icon: Int
}

object Home : TopLevelRoute {
    override val icon: Int
        get() = R.drawable.ic_1
}

object Standings : TopLevelRoute {
    override val icon: Int
        get() = R.drawable.ic_20
}

data class GameStatistics(val game: Game)

data object Login

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Home, Standings)

class TopLevelBackStack(
    startTopLevel: TopLevelRoute
) {
    private val topLevelStacks: LinkedHashMap<TopLevelRoute, SnapshotStateList<Any>> =
        linkedMapOf(startTopLevel to mutableStateListOf(startTopLevel))

    var topLevelKey by mutableStateOf<TopLevelRoute>(startTopLevel)
        private set

    val backStack: SnapshotStateList<Any> = mutableStateListOf(startTopLevel)

    private fun updateBackStack() {
        backStack.clear()
        backStack.addAll(topLevelStacks.values.flatten())
    }

    fun addTopLevel(key: TopLevelRoute) {
        val existing = topLevelStacks[key]
        if (existing == null) {
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            topLevelStacks.remove(key)
            topLevelStacks[key] = existing
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(screen: Any) {
        topLevelStacks[topLevelKey]?.add(screen)
        updateBackStack()
    }

    /** Reset the entire nav state to exactly one top-level stack with its root. */
    fun resetTo(topLevel: TopLevelRoute) {
        topLevelStacks.clear()
        topLevelStacks[topLevel] = mutableStateListOf(topLevel)
        topLevelKey = topLevel
        updateBackStack()
    }

    /** Reset to login (no bottom tabs). */
    fun resetToLogin() {
        topLevelStacks.clear()
        // store Login as a one-item "stack" under a pseudo top-level key
        // easiest is to keep Login in backStack directly and use a flag in UI.
        backStack.clear()
        backStack.add(Login)
    }

    fun canGoBack(): Boolean {
        val current = topLevelStacks[topLevelKey]
        return (current != null && current.size > 1) || topLevelStacks.size > 1
    }

    fun popBack() {
        val current = topLevelStacks[topLevelKey]

        // 1) pop within current top-level stack
        if (current != null && current.size > 1) {
            current.removeAt(current.lastIndex)
            updateBackStack()
            return
        }

        // 2) if cannot pop within, switch to previous top-level stack
        if (topLevelStacks.size > 1) {
            topLevelStacks.remove(topLevelKey)
            topLevelKey = topLevelStacks.keys.last()
            updateBackStack()
        }
        // 3) else do nothing (at root)
    }
}


