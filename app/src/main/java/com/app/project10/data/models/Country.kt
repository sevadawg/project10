package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class Country (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("code" ) var code : String? = null,
    @SerializedName("flag" ) var flag : String? = null

)
