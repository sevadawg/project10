package com.app.nbanow.data.models

import com.google.gson.annotations.SerializedName


data class GamesStatus (

    @SerializedName("long"  ) var long  : String? = null,
    @SerializedName("short" ) var short : String? = null,
    @SerializedName("timer" ) var timer : String? = null

)
