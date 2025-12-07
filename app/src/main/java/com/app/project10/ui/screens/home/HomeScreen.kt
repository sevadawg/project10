package com.app.project10.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.project10.data.models.GamesResponse
import com.app.project10.ui.components.calendar.SingleLineCalendar
import com.app.project10.ui.components.state.Content
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: MainScreenViewModel = koinViewModel(), innerPadding: PaddingValues) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is MainScreenState.DisplayingGames -> Content({
            Screen(
                games = (state as MainScreenState.DisplayingGames).todayGames,
                onFetchData = { selectedDate ->

                }, innerPadding = innerPadding
            )
        })

        is MainScreenState.Loading -> Loading()
        is MainScreenState.DisplayingError -> Error {

        }
    }
}

@Composable
private fun Screen(
    games: List<GamesResponse>,
    onFetchData: (selectedDate: LocalDate) -> Unit,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        SingleLineCalendar { selectedDate ->
            onFetchData(selectedDate)
        }
        GamesList(games = games)
    }
}

@Composable
private fun Header() {
    Text(text = "NBA Now")
}

@Composable
private fun GamesList(games: List<GamesResponse>) {
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed(games) { index, game ->
            GameView(
                index = index,
                game = game,
                isExpanded = selectedIndex == index,
                onClick = {
                    selectedIndex = if (selectedIndex == index) null else index
                },
                listState = listState
            )
        }
    }
}

@Composable
private fun GameView(
    index: Int,
    game: GamesResponse,
    isExpanded: Boolean,
    onClick: () -> Unit,
    listState: LazyListState
) {

}