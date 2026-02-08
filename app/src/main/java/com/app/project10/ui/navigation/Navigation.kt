package com.app.project10.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.app.project10.ui.screens.game.GameScreen
import com.app.project10.ui.screens.home.MainScreen
import com.app.project10.ui.screens.login.LoginScreen
import com.app.project10.ui.screens.saved.SavedScreen

@Composable
fun Navigation() {

    val topLevelBackStack = remember { TopLevelBackStack<Any>(Login) }

    BackHandler {
        topLevelBackStack.removeLast()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(), bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(80.dp)
            ) {
                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                    val isSelected = topLevelRoute == topLevelBackStack.topLevelKey

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            topLevelBackStack.addTopLevel(topLevelRoute)
                        },
                        icon = {
                            topLevelRoute.icon
                        }
                    )
                }
            }
        }) { paddingValues ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<Home> {
                    MainScreen(innerPadding = paddingValues) { game ->
                        topLevelBackStack.add(GameStatistics(game))
                    }
                }
                entry<Saved> {
                    SavedScreen()
                }
                entry<GameStatistics> { params ->
                    val game = params.game
                    GameScreen(
                        game = game,
                        paddings = paddingValues,
                        onBack = {
                            topLevelBackStack.removeLast()
                        }
                    )
                }
                entry<Login> {
                    LoginScreen(onLoginSuccess = {

                    })
                }
            }
        )
    }
}