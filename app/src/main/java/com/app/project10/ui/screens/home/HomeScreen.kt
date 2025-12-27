package com.app.project10.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.project10.data.dto.Game
import com.app.project10.ui.components.calendar.SingleLineCalendar
import com.app.project10.ui.components.state.Content
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onItemClicked: (Game) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is MainScreenState.DisplayingGames -> Content({
            GamesList(
                games = (state as MainScreenState.DisplayingGames).games,
                onDateChanged = { selectedDate ->
                    viewModel.onDateChanged(selectedDate)
                },
                innerPadding = innerPadding,
                onItemClicked = { game ->
                    onItemClicked(game)
                }
            )
        })

        is MainScreenState.Loading -> Loading()
        is MainScreenState.DisplayingError -> Error(
            onRefresh = viewModel::onRefresh
        )
    }
}

@Composable
private fun GamesList(
    games: List<Game>,
    onDateChanged: (selectedDate: LocalDate) -> Unit,
    innerPadding: PaddingValues,
    onItemClicked: (Game) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        SingleLineCalendar { selectedDate ->
            onDateChanged(selectedDate)
        }
        GamesList(games = games) { game ->
            onItemClicked(game)
        }
    }
}

@Composable
private fun Header() {
    Text(text = "NBA Now")
}

@Composable
private fun GamesList(games: List<Game>, onItemClicked: (Game) -> Unit) {
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed(games) { index, game ->
            GameCard(
                modifier = Modifier.padding(bottom = 8.dp),
                itemIndex = index,
                leftTeamName = game.teams.home.name ?: "Team A",
                rightTeamName = game.teams.visitors.name ?: "Team B",
                leftScore = game.scores.home.points,
                rightScore = game.scores.visitors.points,
                gameTime = game.date.start?.substring(11, 16) ?: "--:--",
                gameStatus = game.status.long ?: "TBD"
            ) {
                onItemClicked(games[index])
            }
        }
    }
}

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    itemIndex: Int,
    leftTeamName: String = "Team A",
    rightTeamName: String = "Team B",
    leftScore: Int,
    rightScore: Int,
    gameTime: String,
    gameStatus: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .height(26.dp)
                            .weight(4f), text = leftTeamName
                    )
                    Text(
                        modifier = Modifier
                            .height(26.dp)
                            .weight(1f), text = leftScore.toString()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .height(26.dp)
                            .weight(4f), text = rightTeamName
                    )
                    Text(
                        modifier = Modifier
                            .height(26.dp)
                            .weight(1f), text = rightScore.toString()
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = gameTime)
                Text(text = gameStatus)
            }
        }
    }
}
