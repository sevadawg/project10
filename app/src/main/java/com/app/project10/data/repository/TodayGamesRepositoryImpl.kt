package com.app.project10.data.repository

import com.app.project10.data.models.Away
import com.app.project10.data.models.AwayScore
import com.app.project10.data.models.GamesResponse
import com.app.project10.data.models.Home
import com.app.project10.data.models.HomeScore
import com.app.project10.data.models.Scores
import com.app.project10.data.models.Teams
import com.app.project10.network.base.Result
import com.app.project10.network.services.GamesNetworkService

import com.app.project10.network.base.Error

class TodayGamesRepositoryImpl (private val gamesNetworkService: GamesNetworkService) :
    TodayGamesRepository {
    override suspend fun getTodayGames(todayDate: String): Result<List<GamesResponse>, Error> {

        val mockGames = listOf(
            // 2025-09-03
            GamesResponse(time = "13:00PM", date = "2025-09-03", teams = Teams(home = Home(name = "Toronto Raptors"), away = Away(name = "Los Angeles Lakers")), scores = Scores(home = HomeScore(total = 110), away = AwayScore(total = 100))),
            GamesResponse(time = "15:00PM", date = "2025-09-03", teams = Teams(home = Home(name = "Boston Celtics"), away = Away(name = "Miami Heat")), scores = Scores(home = HomeScore(total = 98), away = AwayScore(total = 102))),
            GamesResponse(time = "17:00PM", date = "2025-09-03", teams = Teams(home = Home(name = "Chicago Bulls"), away = Away(name = "Brooklyn Nets")), scores = Scores(home = HomeScore(total = 105), away = AwayScore(total = 97))),
            GamesResponse(time = "19:00PM", date = "2025-09-03", teams = Teams(home = Home(name = "Dallas Mavericks"), away = Away(name = "Phoenix Suns")), scores = Scores(home = HomeScore(total = 112), away = AwayScore(total = 115))),
            GamesResponse(time = "21:00PM", date = "2025-09-03", teams = Teams(home = Home(name = "Denver Nuggets"), away = Away(name = "Golden State Warriors")), scores = Scores(home = HomeScore(total = 120), away = AwayScore(total = 123))),
            GamesResponse(time = "22:30PM", date = "2025-09-03", teams = Teams(home = Home(name = "San Antonio Spurs"), away = Away(name = "New Orleans Pelicans")), scores = Scores(home = HomeScore(total = 89), away = AwayScore(total = 91))),
            GamesResponse(time = "23:59PM", date = "2025-09-03", teams = Teams(home = Home(name = "Utah Jazz"), away = Away(name = "Portland Trail Blazers")), scores = Scores(home = HomeScore(total = 99), away = AwayScore(total = 104))),

            // 2025-09-04
            GamesResponse(time = "14:00PM", date = "2025-09-04", teams = Teams(home = Home(name = "Atlanta Hawks"), away = Away(name = "Charlotte Hornets")), scores = Scores(home = HomeScore(total = 102), away = AwayScore(total = 99))),
            GamesResponse(time = "16:00PM", date = "2025-09-04", teams = Teams(home = Home(name = "Cleveland Cavaliers"), away = Away(name = "Detroit Pistons")), scores = Scores(home = HomeScore(total = 101), away = AwayScore(total = 95))),
            GamesResponse(time = "18:00PM", date = "2025-09-04", teams = Teams(home = Home(name = "Indiana Pacers"), away = Away(name = "Orlando Magic")), scores = Scores(home = HomeScore(total = 108), away = AwayScore(total = 103))),
            GamesResponse(time = "20:00PM", date = "2025-09-04", teams = Teams(home = Home(name = "Milwaukee Bucks"), away = Away(name = "Philadelphia 76ers")), scores = Scores(home = HomeScore(total = 115), away = AwayScore(total = 118))),
            GamesResponse(time = "22:00PM", date = "2025-09-04", teams = Teams(home = Home(name = "Sacramento Kings"), away = Away(name = "Houston Rockets")), scores = Scores(home = HomeScore(total = 95), away = AwayScore(total = 90))),

            // 2025-09-05
            GamesResponse(time = "13:30PM", date = "2025-09-05", teams = Teams(home = Home(name = "Toronto Raptors"), away = Away(name = "Brooklyn Nets")), scores = Scores(home = HomeScore(total = 102), away = AwayScore(total = 106))),
            GamesResponse(time = "15:30PM", date = "2025-09-05", teams = Teams(home = Home(name = "Golden State Warriors"), away = Away(name = "Memphis Grizzlies")), scores = Scores(home = HomeScore(total = 121), away = AwayScore(total = 119))),
            GamesResponse(time = "18:00PM", date = "2025-09-05", teams = Teams(home = Home(name = "Phoenix Suns"), away = Away(name = "Denver Nuggets")), scores = Scores(home = HomeScore(total = 98), away = AwayScore(total = 100))),
            GamesResponse(time = "20:00PM", date = "2025-09-05", teams = Teams(home = Home(name = "Los Angeles Clippers"), away = Away(name = "Utah Jazz")), scores = Scores(home = HomeScore(total = 99), away = AwayScore(total = 95))),
            GamesResponse(time = "22:00PM", date = "2025-09-05", teams = Teams(home = Home(name = "Minnesota Timberwolves"), away = Away(name = "Oklahoma City Thunder")), scores = Scores(home = HomeScore(total = 91), away = AwayScore(total = 94))),

            // 2025-09-06
            GamesResponse(time = "14:00PM", date = "2025-09-06", teams = Teams(home = Home(name = "Chicago Bulls"), away = Away(name = "Indiana Pacers")), scores = Scores(home = HomeScore(total = 95), away = AwayScore(total = 89))),
            GamesResponse(time = "16:00PM", date = "2025-09-06", teams = Teams(home = Home(name = "Miami Heat"), away = Away(name = "Atlanta Hawks")), scores = Scores(home = HomeScore(total = 104), away = AwayScore(total = 102))),
            GamesResponse(time = "18:00PM", date = "2025-09-06", teams = Teams(home = Home(name = "Detroit Pistons"), away = Away(name = "Milwaukee Bucks")), scores = Scores(home = HomeScore(total = 88), away = AwayScore(total = 92))),
            GamesResponse(time = "20:30PM", date = "2025-09-06", teams = Teams(home = Home(name = "Boston Celtics"), away = Away(name = "Orlando Magic")), scores = Scores(home = HomeScore(total = 107), away = AwayScore(total = 100))),

            // 2025-09-07
            GamesResponse(time = "13:00PM", date = "2025-09-07", teams = Teams(home = Home(name = "Charlotte Hornets"), away = Away(name = "New York Knicks")), scores = Scores(home = HomeScore(total = 97), away = AwayScore(total = 101))),
            GamesResponse(time = "15:00PM", date = "2025-09-07", teams = Teams(home = Home(name = "Philadelphia 76ers"), away = Away(name = "Cleveland Cavaliers")), scores = Scores(home = HomeScore(total = 108), away = AwayScore(total = 105))),
            GamesResponse(time = "17:00PM", date = "2025-09-07", teams = Teams(home = Home(name = "San Antonio Spurs"), away = Away(name = "Houston Rockets")), scores = Scores(home = HomeScore(total = 103), away = AwayScore(total = 96))))

        return Result.Success(
            mockGames.filter { it.date == todayDate }
        )
//        return try {
//            val response = gamesNetworkService.getTodayGames(todayDate)
//
//            if (response.isSuccessful && response.body()?.errors.isNullOrEmpty() && response.body()?.response != null) {
//                Result.Success(response.body()!!.response)
//            } else {
//                Result.Failure(Error.ResponseError)
//            }
//        } catch (error: Exception) {
//            Result.Failure(Error.NetworkError)
//        }
    }
}