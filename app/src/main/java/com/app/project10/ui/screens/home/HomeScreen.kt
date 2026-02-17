package com.app.project10.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.project10.data.dto.game.Game
import com.app.project10.ui.components.calendar.SingleLineCalendar
import com.app.project10.ui.components.common.AppCard
import com.app.project10.ui.components.common.AppSectionTitle
import com.app.project10.ui.components.state.Content
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import com.app.project10.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onItemClicked: (Game) -> Unit
) {
    val dimens = Dimens.current
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = dimens.md)
    ) {
        Header()
        SingleLineCalendar { date ->
            viewModel.onDateChanged(date)
        }
        when (state) {
            is MainScreenState.DisplayingGames -> Content({
                val games = (state as MainScreenState.DisplayingGames).games
                GamesList(games = games) { game ->
                    onItemClicked(game)
                }
            })

            is MainScreenState.Loading -> Loading()
            is MainScreenState.DisplayingError -> Error(
                onRefresh = viewModel::onRefresh
            )
        }
    }
}

@Composable
private fun Header() {
    val dimens = Dimens.current
    AppSectionTitle(
        title = "NBA Now",
        modifier = Modifier.padding(vertical = dimens.md)
    )
}

@Composable
private fun GamesList(games: List<Game>, onItemClicked: (Game) -> Unit) {
    val dimens = Dimens.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(games) { index, game ->
            GameCard(
                modifier = Modifier.padding(bottom = dimens.xs),
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
    val dimens = Dimens.current
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .height(dimens.listCardHeight),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimens.md, vertical = dimens.xs)
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
                            .height(dimens.teamRowHeight)
                            .weight(4f),
                        text = leftTeamName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        modifier = Modifier
                            .height(dimens.teamRowHeight)
                            .weight(1f),
                        text = leftScore.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(dimens.xs))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .height(dimens.teamRowHeight)
                            .weight(4f),
                        text = rightTeamName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        modifier = Modifier
                            .height(dimens.teamRowHeight)
                            .weight(1f),
                        text = rightScore.toString(),
                        style = MaterialTheme.typography.titleMedium
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
                Text(text = gameTime, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = gameStatus,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
