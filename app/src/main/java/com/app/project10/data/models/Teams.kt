package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class Teams (

    @SerializedName("home" ) var home : Home? = Home(),
    @SerializedName("away" ) var away : Away? = Away()

)
