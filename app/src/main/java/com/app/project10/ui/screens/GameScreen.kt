package com.app.project10.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.project10.data.dto.Game
import com.app.project10.utils.TeamStats

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    paddings: PaddingValues,
    game: Game,
    onBack: () -> Unit
) {
    GameStat(modifier = modifier.padding(paddings), game = game)
}

@Composable
private fun GameStat(modifier: Modifier = Modifier, game: Game) {

    Column(modifier = modifier.fillMaxSize()) {
        ScoreboardPanel(modifier = modifier, game = game)

        TeamComparisonPanel(
            home = TeamStats(2, 4, 5, 6, 7),
            away = TeamStats(5, 6, 7, 8, 9)
        )
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
            .padding(12.dp)
    ) {

        Text(
            text = "TEAM COMPARISON",
            color = Color.Yellow,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        ComparisonHeader()

        Spacer(Modifier.height(8.dp))

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
//        modifier = Modifier.weight(1f),
        color = Color.White,
        fontSize = 12.sp,
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
            .padding(vertical = 4.dp),
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
            fontSize = 14.sp,
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
//        modifier = Modifier.weight(1f),
        color = Color.Red,
        fontSize = 18.sp,
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
            .padding(16.dp)
    ) {

        // ===== Header =====
        Text(
            text = "HOME OF THE TIGERS",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

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
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow
                )

                Spacer(Modifier.height(8.dp))

                // Home / Period / Guest
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TeamScore("HOME", "15")
                    Period("3")
                    TeamScore("GUEST", "18")
                }

                Spacer(Modifier.height(12.dp))

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
        Text(label, color = Color.White, fontSize = 14.sp)
        Text(
            score,
            color = Color.Red,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Period(value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("PERIOD", color = Color.White, fontSize = 14.sp)
        Text(
            value,
            color = Color.Yellow,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LabelValue(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.White, fontSize = 12.sp)
        Text(
            value,
            color = Color.Red,
            fontSize = 20.sp,
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
        Text("PLR  PTS", color = Color.White, fontSize = 12.sp)

        repeat(5) {
            Text(
                text = "${it + 1}   ${listOf(2, 4, 6, 8, 0)[it]}",
                color = Color.Red,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}