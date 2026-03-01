package com.app.project10.data.remote.dto.standings

data class StandingsResponse(
    val response: List<Standing> = emptyList()
)

data class Standing(
    val conference: StandingConference? = null,
    val division: StandingDivision? = null,
    val gamesBehind: String? = null,
    val loss: StandingRecord? = null,
    val team: StandingTeam? = null,
    val win: StandingRecord? = null
)

data class StandingConference(
    val name: String? = null,
    val rank: Int? = null
)

data class StandingDivision(
    val name: String? = null,
    val rank: Int? = null
)

data class StandingRecord(
    val total: String? = null,
    val percentage: String? = null
)

data class StandingTeam(
    val id: Int? = null,
    val logo: String? = null,
    val name: String? = null,
    val nickname: String? = null
)

