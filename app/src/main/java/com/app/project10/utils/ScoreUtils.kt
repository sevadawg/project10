package com.app.project10.utils

import com.app.project10.data.models.Game

fun Game.toStatsHome() {
    val score = TeamStats()

    with(score) {

    }
}

data class TeamStats(
    val rebounds: Int = 0,
    val assists: Int = 0,
    val turnovers: Int = 0,
    val fgPct: Int = 0,
    val threes: Int = 0
)
