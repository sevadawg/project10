package com.app.project10.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.app.project10.presentation.auth.AuthState
import com.app.project10.presentation.screens.game.GameScreen
import com.app.project10.presentation.screens.home.HomeScreen
import com.app.project10.presentation.screens.login.LoginScreen
import com.app.project10.presentation.screens.saved.SavedScreen
import com.app.project10.presentation.theme.Dimens

@Composable
fun Navigation(
    authState: AuthState,
    onLoginSuccess: () -> Unit
) {
    val dimens = Dimens.current
    val backStack = remember { TopLevelBackStack(Home) }

    LaunchedEffect(authState) {
        when (authState) {
            AuthState.Loading -> Unit
            AuthState.Authenticated -> backStack.resetTo(Home)
            AuthState.Unauthenticated -> backStack.resetToLogin()
        }
    }

    if (authState == AuthState.Loading) return

    val showBottomBar = authState == AuthState.Authenticated

    BackHandler(enabled = showBottomBar && backStack.canGoBack()) {
        backStack.popBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(modifier = Modifier.height(dimens.navBarHeight)) {
                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                        val isSelected = topLevelRoute == backStack.topLevelKey
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { backStack.addTopLevel(topLevelRoute) },
                            icon = { /* your icon composable */ }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavDisplay(
            backStack = backStack.backStack,
            onBack = { backStack.popBack() },
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen(innerPadding = paddingValues) { game ->
                        backStack.add(GameStatistics(game))
                    }
                }
                entry<Saved> { SavedScreen() }
                entry<GameStatistics> { params ->
                    GameScreen(
                        game = params.game,
                        paddings = paddingValues,
                        onBack = { backStack.popBack() }
                    )
                }
                entry<Login> {
                    LoginScreen(onLoginSuccess = {
                        onLoginSuccess()
                    })
                }
            }
        )
    }
}


