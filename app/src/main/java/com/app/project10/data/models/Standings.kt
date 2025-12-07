package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class StandingsModel(
    @SerializedName("get") var get: String? = null,
    @SerializedName("parameters") var parameters : StandingsParameters? = StandingsParameters(),
    @SerializedName("results") var results: Int? = null,
    @SerializedName("response") var response: ArrayList<ArrayList<StandingsResponse>> = arrayListOf()
) {
    companion object {
        val Empty: StandingsModel = StandingsModel(
            get = null,
            parameters = StandingsParameters(),
            results = null,
            response = arrayListOf()
        )
    }
}

data class StandingsParameters(
    var league: String? = null,
    var season: String? = null
)

data class StandingsResponse(
    var position: Int? = null,
    var stage: String? = "",
    var group: Group = Group(),
    var team: Team = Team(),
    var league: League = League(),
    var country: Country = Country(),
    var games: Games = Games(),
    var points: Points = Points()
    )

data class Group(
    var name: String? = "",
    var points: Int? = 0
)

data class Team(
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("logo" ) var logo : String? = null
)

data class Games(
    var played: Int? = 0,
    var win: WinLoseModel = WinLoseModel(),
    var lose: WinLoseModel = WinLoseModel()
)

data class WinLoseModel(
    var total: Int? = 0,
    var percentage: String? = ""
)

data class Points(
    @SerializedName("for") var pointsFor: Int? = 0,
    @SerializedName("against") var pointsAgainst: Int? = 0
)