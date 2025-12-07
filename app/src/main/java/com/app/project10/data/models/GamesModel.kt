package com.app.project10.data.models

import com.google.gson.annotations.SerializedName

data class GamesModel(
    @SerializedName("get") var get: String? = null,
    @SerializedName("parameters") var parameters: GamesParameters? = GamesParameters(),
    @SerializedName("results") var results: Int? = null,
    @SerializedName("response") var response: ArrayList<GamesResponse> = arrayListOf(),
    @SerializedName("errors") var errors: List<String>
) {
    companion object {
        val Empty: GamesModel = GamesModel(
                get = null,
                parameters = GamesParameters(),
                results = null,
                response = arrayListOf(),
                errors = arrayListOf()
        )
    }
}