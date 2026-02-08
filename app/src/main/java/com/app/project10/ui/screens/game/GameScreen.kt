package com.app.project10.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.core.utils.TeamStats
import com.app.project10.data.dto.game.Game
import com.app.project10.ui.components.state.Error
import com.app.project10.ui.components.state.Loading
import com.app.project10.ui.theme.Dimens
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
                ScoreboardPanel(game = currentState.game)
                Spacer(modifier = Modifier.height(Dimens.SpacerSmall))
                TeamComparisonPanel(
                    home = TeamStats(2, 4, 5, 6, 7), // Placeholder
                    away = TeamStats(5, 6, 7, 8, 9)  // Placeholder
                )
            }
            is GameScreenState.DisplayingError -> Error(onRefresh = viewModel::onRefresh)
            is GameScreenState.Loading -> Loading()
        }
    }
}

@Composable
fun TeamComparisonPanel(
    home: TeamStats,
    away: TeamStats
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1A3A))
            .padding(Dimens.PaddingMedium)
    ) {

        Text(
            text = "TEAM COMPARISON",
            color = Color.Yellow,
            fontSize = Dimens.FontSizeNormal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(Dimens.PaddingMedium))

        ComparisonHeader()

        Spacer(Modifier.height(Dimens.SpacerSmall))

        ComparisonRow(home.rebounds, "REBOUNDS", away.rebounds)
        ComparisonRow(home.assists, "ASSISTS", away.assists)
        ComparisonRow(home.turnovers, "TURNOVERS", away.turnovers)
        ComparisonRow("${home.fgPct}%", "FG %", "${away.fgPct}%")
        ComparisonRow(home.threes, "3PT MADE", away.threes)
    }
}

@Composable
fun ComparisonHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderCell("HOME", Alignment.Start)
        HeaderCell("STAT", Alignment.CenterHorizontally)
        HeaderCell("AWAY", Alignment.End)
    }
}

@Composable
fun HeaderCell(text: String, align: Alignment.Horizontal) {
    Text(
        text = text,
        color = Color.White,
        fontSize = Dimens.FontSizeCaption,
        fontFamily = FontFamily.Monospace,
        textAlign = when (align) {
            Alignment.Start -> TextAlign.Start
            Alignment.End -> TextAlign.End
            else -> TextAlign.Center
        }
    )
}

@Composable
fun ComparisonRow(
    homeValue: Any,
    label: String,
    awayValue: Any
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingExtraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {

        StatValue(
            text = homeValue.toString(),
            align = TextAlign.Start
        )

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = Color.Yellow,
            fontSize = Dimens.FontSizeBody,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )

        StatValue(
            text = awayValue.toString(),
            align = TextAlign.End
        )
    }
}

@Composable
fun StatValue(
    text: String,
    align: TextAlign
) {
    Text(
        text = text,
        color = Color.Red,
        fontSize = Dimens.FontSizeMedium,
        fontWeight = FontWeight.Bold,
        textAlign = align,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun ScoreboardPanel(
    modifier: Modifier = Modifier,
    game: Game
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1A3A)) // dark blue
            .padding(Dimens.PaddingNormal)
    ) {

        // ===== Header =====
        Text(
            text = "HOME OF THE TIGERS",
            color = Color.White,
            fontSize = Dimens.FontSizeHeadline,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(Dimens.PaddingMedium))

        // ===== Main Row =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ===== Left Player Stats =====
            PlayerStatsColumn(
                modifier = Modifier.weight(1f),
                align = Alignment.Start
            )

            // ===== Center Score Area =====
            Column(
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Time
                Text(
                    text = "4:53",
                    fontSize = Dimens.FontSizeDisplay,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow
                )

                Spacer(Modifier.height(Dimens.SpacerSmall))

                // Home / Period / Guest
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TeamScore("HOME", "15")
                    Period("3")
                    TeamScore("GUEST", "18")
                }

                Spacer(Modifier.height(Dimens.PaddingMedium))

                // Fouls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LabelValue("FOULS", "0")
                    LabelValue("FOULS", "0")
                }
            }

            // ===== Right Player Stats =====
            PlayerStatsColumn(
                modifier = Modifier.weight(1f),
                align = Alignment.End
            )
        }
    }
}

@Composable
fun TeamScore(label: String, score: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.White, fontSize = Dimens.FontSizeBody)
        Text(
            score,
            color = Color.Red,
            fontSize = Dimens.FontSizeDisplaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Period(value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("PERIOD", color = Color.White, fontSize = Dimens.FontSizeBody)
        Text(
            value,
            color = Color.Yellow,
            fontSize = Dimens.FontSizeDisplaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LabelValue(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.White, fontSize = Dimens.FontSizeCaption)
        Text(
            value,
            color = Color.Red,
            fontSize = Dimens.FontSizeHeadline,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlayerStatsColumn(
    modifier: Modifier = Modifier,
    align: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = align
    ) {
        Text("PLR  PTS", color = Color.White, fontSize = Dimens.FontSizeCaption)

        repeat(5) {
            Text(
                text = "${it + 1}   ${listOf(2, 4, 6, 8, 0)[it]}",
                color = Color.Red,
                fontSize = Dimens.FontSizeBody,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
