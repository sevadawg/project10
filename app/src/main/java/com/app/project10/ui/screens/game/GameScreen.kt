package com.app.project10.ui.screens.game

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.data.dto.game.Game
import com.app.project10.data.dto.game.TeamDetails
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    paddings: PaddingValues,
    game: Game,
    onBack: () -> Unit
) {
    GameStat(modifier = modifier.padding(paddings), initialGameId = game.id)
}

@Composable
private fun GameStat(
    modifier: Modifier = Modifier,
    initialGameId: Int,
    viewModel: GameViewModel = koinViewModel()
) {
    LaunchedEffect(initialGameId) {
        viewModel.setGameId(initialGameId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        when (val currentState = state) {
            is GameScreenState.DisplayingGame -> {
                Stats(game = currentState.game)
            }
            is GameScreenState.DisplayingError -> Error(onRefresh = viewModel::onRefresh)
            is GameScreenState.Loading -> Loading()
        }
    }
}

@Composable
fun Stats(game: Game, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { GameHeader(game = game) }
        item { GameInfo(game = game) }
        item { ScoreDetails(game = game) }
        item { OfficialsAndStats(game = game) }
    }
}

@Composable
fun GameHeader(game: Game) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamColumn(team = game.teams.visitors, score = game.scores.visitors.points)
        Text(
            text = "${game.scores.home.points} - ${game.scores.visitors.points}",
            style = MaterialTheme.typography.bodyLarge
        )
        TeamColumn(team = game.teams.home, score = game.scores.home.points)
    }
}

@Composable
fun TeamColumn(team: TeamDetails, score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(4.dp))
        Text(text = team.nickname ?: "", style = MaterialTheme.typography.bodySmall)
        Text(text = "Score: $score", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun GameInfo(game: Game) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Arena: ${game.arena.name}, ${game.arena.city}",
            style = MaterialTheme.typography.bodySmall
        )
        Text("Status: ${game.status.long}", style = MaterialTheme.typography.bodySmall)
        Text(
            "Period: ${game.periods.current}/${game.periods.total}",
            style = MaterialTheme.typography.bodySmall
        )
        Text("Duration: ${game.date.duration}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ScoreDetails(game: Game) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Line Scores:", style = MaterialTheme.typography.labelLarge)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(game.teams.visitors.nickname ?: "")
                game.scores.visitors.linescore.forEach { Text(it) }
            }
            Column {
                Text(game.teams.home.nickname ?: "")
                game.scores.home.linescore.forEach { Text(it) }
            }
        }
    }
}

@Composable
fun OfficialsAndStats(game: Game) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Officials: ${game.officials.joinToString(", ")}")
        Text("Times Tied: ${game.timesTied}")
        Text("Lead Changes: ${game.leadChanges}")
        game.nugget?.let { Text("Nugget: $it") }
    }
}
