package com.app.project10.presentation.screens.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.data.remote.dto.standings.Standing
import com.app.project10.presentation.components.common.AppCard
import com.app.project10.presentation.components.common.AppSectionTitle
import com.app.project10.presentation.components.state.Error
import com.app.project10.presentation.components.state.Loading
import com.app.project10.presentation.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun StandingsScreen(
    innerPadding: PaddingValues,
    viewModel: StandingsViewModel = koinViewModel()
) {
    val dimens = Dimens.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = dimens.md)
    ) {
        AppSectionTitle(
            title = "Standings",
            modifier = Modifier.padding(vertical = dimens.md)
        )

        when (state) {
            is StandingsScreenState.DisplayingStandings -> {
                val standings = (state as StandingsScreenState.DisplayingStandings).standings
                StandingsList(standings = standings)
            }

            StandingsScreenState.Loading -> Loading()
            is StandingsScreenState.DisplayingError -> Error(onRefresh = viewModel::onRefresh)
        }
    }
}

@Composable
private fun StandingsList(standings: List<Standing>) {
    val dimens = Dimens.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = dimens.xs),
        verticalArrangement = Arrangement.spacedBy(dimens.xs)
    ) {
        items(
            items = standings.sortedBy { it.conference?.rank ?: Int.MAX_VALUE },
            key = { standing -> "${standing.team?.id ?: -1}-${standing.conference?.rank ?: -1}" }
        ) { standing ->
            StandingCard(standing = standing)
        }
    }
}

@Composable
private fun StandingCard(standing: Standing) {
    val dimens = Dimens.current
    val rank = standing.conference?.rank?.toString() ?: "-"
    val teamName = standing.team?.name ?: "Unknown Team"
    val wins = standing.win?.total ?: "-"
    val losses = standing.loss?.total ?: "-"

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.md, vertical = dimens.sm)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "#$rank",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.weight(4f),
                text = teamName,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = "W $wins",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.weight(2f),
                text = "L $losses",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

