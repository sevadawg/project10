package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class AwayScore (

    @SerializedName("quarter_1" ) var quarter1 : Int?    = null,
    @SerializedName("quarter_2" ) var quarter2 : Int?    = null,
    @SerializedName("quarter_3" ) var quarter3 : Int?    = null,
    @SerializedName("quarter_4" ) var quarter4 : Int?    = null,
    @SerializedName("over_time" ) var overTime : String? = null,
    @SerializedName("total"     ) var total    : Int?    = null

)
