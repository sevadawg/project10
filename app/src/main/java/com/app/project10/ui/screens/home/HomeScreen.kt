package com.app.project10.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.project10.R
import com.app.project10.data.models.GamesResponse
import com.app.project10.ui.components.calendar.SingleLineCalendar
import com.app.project10.ui.components.state.Content
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import com.app.project10.ui.theme.PurpleGrey80
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: MainScreenViewModel = koinViewModel(), innerPadding: PaddingValues) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is MainScreenState.DisplayingGames -> Content({
            GamesList(
                games = (state as MainScreenState.DisplayingGames).todayGames,
                onDateChanged = { selectedDate ->
                    viewModel.onDateChanged(selectedDate)
                },
                innerPadding = innerPadding
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
    games: List<GamesResponse>,
    onDateChanged: (selectedDate: LocalDate) -> Unit,
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
            onDateChanged(selectedDate)
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
            GameCard(
                leftTeamLogo = painterResource(R.drawable.ic_arrow_back),
                rightTeamLogo = painterResource(R.drawable.ic_arrow_forward),
                leftScore = game.scores.home?.total ?: 0,
                rightScore = game.scores.away?.total ?: 0,
                gameTime = game.time.toString(),
                gameStatus = game.status.short ?: "TDA",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun GameCard(
    leftTeamLogo: Painter,
    rightTeamLogo: Painter,
    leftScore: Int,
    rightScore: Int,
    gameTime: String,   // "12:30"
    gameStatus: String, // "FINAL"
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PurpleGrey80)
                .padding(16.dp)
        ) {

            // TOP ROW — LOGOS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = leftTeamLogo,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
                Image(
                    painter = rightTeamLogo,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }

            // CENTER — SCORES + TIME
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = leftScore.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = gameTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = rightScore.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // BOTTOM-RIGHT — STATUS
            Text(
                text = gameStatus,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}