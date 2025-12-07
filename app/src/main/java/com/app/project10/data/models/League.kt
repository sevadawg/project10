package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class League (

    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("type"   ) var type   : String? = null,
    @SerializedName("season" ) var season : String? = null,
    @SerializedName("logo"   ) var logo   : String? = null

)
