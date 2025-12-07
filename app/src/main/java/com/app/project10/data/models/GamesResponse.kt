package com.app.project10.data.models

import com.app.nbanow.data.models.GamesStatus

data class GamesResponse(
    val id: Int? = null,
    val date: String? = null,
    val time: String? = null,
    val timestamp: Long? = null,
    val timezone: String? = null,
    val stage: String? = null,
    val week: String? = null,
    val status: GamesStatus = GamesStatus(),
    val league: League = League(),
    val country: Country = Country(),
    val teams: Teams = Teams(),
    val scores: Scores = Scores()
)