package com.app.project10.presentation.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.R
import com.app.project10.data.remote.dto.game.Game
import com.app.project10.presentation.components.common.AppCard
import com.app.project10.presentation.components.common.AppSectionTitle
import com.app.project10.presentation.components.state.Error
import com.app.project10.presentation.components.state.Loading
import com.app.project10.presentation.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    paddings: PaddingValues,
    game: Game,
    onBack: () -> Unit
) {
    GameDetailsRoute(
        modifier = modifier.padding(paddings),
        initialGameId = game.id,
        onBack = onBack
    )
}

@Composable
private fun GameDetailsRoute(
    modifier: Modifier = Modifier,
    initialGameId: Int,
    onBack: () -> Unit,
    viewModel: GameViewModel = koinViewModel()
) {
    LaunchedEffect(initialGameId) {
        viewModel.setGameId(initialGameId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        when (val currentState = state) {
            is GameScreenState.DisplayingGame -> {
                GameDetailsScreen(
                    game = currentState.game,
                    onBack = onBack
                )
            }

            is GameScreenState.DisplayingError -> Error(onRefresh = viewModel::onRefresh)
            is GameScreenState.Loading -> Loading()
        }
    }
}

@Composable
private fun GameDetailsScreen(
    game: Game,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = Dimens.current
    val rows = remember(game) { buildComparisonRows(game) }
    val periodRows = remember(game) { buildPeriodRows(game) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimens.md),
        verticalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        item(key = "top-bar") { DetailsTopBar(onBack = onBack) }
        item(key = "score-header") { ScoreHeader(game = game) }
        item(key = "meta") { GameMeta(game = game) }
        item(key = "comparison-card") { ComparisonCard(rows = rows) }
        item(key = "period-card") { PeriodByPeriodCard(rows = periodRows) }
        item(key = "extras") { ExtraInfo(game = game) }
    }
}

@Composable
private fun DetailsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back"
            )
        }
        Text(
            text = "Game Details",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ScoreHeader(game: Game) {
    val dimens = Dimens.current
    AppCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamScore(
                    teamName = game.teams.visitors.nickname ?: game.teams.visitors.name.orEmpty(),
                    points = game.scores.visitors.points,
                    alignEnd = false
                )
                Text(
                    text = "-",
                    style = MaterialTheme.typography.titleLarge
                )
                TeamScore(
                    teamName = game.teams.home.nickname ?: game.teams.home.name.orEmpty(),
                    points = game.scores.home.points,
                    alignEnd = true
                )
            }

            Text(
                text = game.status.long ?: "Unknown status",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TeamScore(teamName: String, points: Int, alignEnd: Boolean) {
    val alignment = if (alignEnd) Alignment.End else Alignment.Start
    Column(horizontalAlignment = alignment) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = points.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun GameMeta(game: Game) {
    val dimens = Dimens.current
    val clockText = game.status.clock?.takeIf { it.isNotBlank() } ?: "--:--"
    val arenaText = listOfNotNull(game.arena.name, game.arena.city)
        .filter { it.isNotBlank() }
        .joinToString(", ")
        .ifBlank { "Unknown arena" }

    AppCard {
        Column(
            modifier = Modifier.padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            MetaRow(label = "Period", value = "${game.periods.current}/${game.periods.total}")
            MetaRow(label = "Clock", value = clockText)
            MetaRow(label = "Duration", value = game.date.duration ?: "-")
            MetaRow(label = "Arena", value = arenaText)
        }
    }
}

@Composable
private fun MetaRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun ComparisonCard(rows: List<ComparisonRowUi>) {
    val dimens = Dimens.current
    AppCard {
        Column(
            modifier = Modifier.padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            AppSectionTitle(title = "Team Comparison")
            rows.forEachIndexed { index, row ->
                ComparisonRow(row = row)
                if (index < rows.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(top = dimens.xxs))
                }
            }
        }
    }
}

@Composable
private fun ComparisonRow(row: ComparisonRowUi) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = row.visitorValue,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = row.label,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = row.homeValue,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PeriodByPeriodCard(rows: List<ComparisonRowUi>) {
    val dimens = Dimens.current
    AppCard {
        Column(
            modifier = Modifier.padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            AppSectionTitle(title = "Period By Period")
            rows.forEach { row ->
                ComparisonRow(row = row)
            }
        }
    }
}

@Composable
private fun ExtraInfo(game: Game) {
    val dimens = Dimens.current
    val officials = game.officials.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "-"
    AppCard {
        Column(
            modifier = Modifier.padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            MetaRow(label = "Officials", value = officials)
            MetaRow(label = "Times Tied", value = game.timesTied.toString())
            MetaRow(label = "Lead Changes", value = game.leadChanges.toString())
            game.nugget?.takeIf { it.isNotBlank() }?.let { nugget ->
                Text(
                    text = nugget,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(dimens.xs)
                        )
                        .padding(dimens.xs)
                )
            }
        }
    }
}

private data class ComparisonRowUi(
    val label: String,
    val visitorValue: String,
    val homeValue: String
)

private fun buildComparisonRows(game: Game): List<ComparisonRowUi> {
    return listOf(
        ComparisonRowUi(
            label = "Points",
            visitorValue = game.scores.visitors.points.toString(),
            homeValue = game.scores.home.points.toString()
        ),
        ComparisonRowUi(
            label = "Record",
            visitorValue = "${game.scores.visitors.win}-${game.scores.visitors.loss}",
            homeValue = "${game.scores.home.win}-${game.scores.home.loss}"
        ),
        ComparisonRowUi(
            label = "Series",
            visitorValue = "${game.scores.visitors.series.win}-${game.scores.visitors.series.loss}",
            homeValue = "${game.scores.home.series.win}-${game.scores.home.series.loss}"
        )
    )
}

private fun buildPeriodRows(game: Game): List<ComparisonRowUi> {
    val visitorScores = game.scores.visitors.linescore
    val homeScores = game.scores.home.linescore
    val maxPeriods = maxOf(visitorScores.size, homeScores.size)
    if (maxPeriods == 0) return listOf(
        ComparisonRowUi(label = "No period stats", visitorValue = "-", homeValue = "-")
    )

    return List(maxPeriods) { index ->
        ComparisonRowUi(
            label = "P${index + 1}",
            visitorValue = visitorScores.getOrNull(index) ?: "-",
            homeValue = homeScores.getOrNull(index) ?: "-"
        )
    }
}


