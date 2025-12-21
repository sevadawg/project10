package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int = 0,
    val league: String? = "",
    val season: Int = 0,
    val date: Date,
    val stage: Int = 0,
    val status: Status,
    val periods: Periods,
    val arena: Arena,
    val teams: Teams,
    val scores: Scores,
    val officials: List<String> = listOf<String>(),
    val timesTied: Int = 0,
    val leadChanges: Int = 0,
    val nugget: String? = ""
)
