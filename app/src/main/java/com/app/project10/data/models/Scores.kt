package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class Scores (

    @SerializedName("home" ) var home : HomeScore? = HomeScore(),
    @SerializedName("away" ) var away : AwayScore? = AwayScore()

)
