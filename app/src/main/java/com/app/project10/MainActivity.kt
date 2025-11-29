package com.app.project10

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.app.project10.navigation.Home
import com.app.project10.navigation.Saved
import com.app.project10.navigation.TOP_LEVEL_ROUTES
import com.app.project10.navigation.TopLevelBackStack
import com.app.project10.ui.screens.HomeScreen
import com.app.project10.ui.screens.SavedScreen
import com.app.project10.ui.theme.Project10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project10Theme {
                App()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
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
    }) { _ ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen()
                }
                entry<Saved> {
                    SavedScreen()
                }
            }
        )

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project10Theme {
        App()
    }
}