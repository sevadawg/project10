package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Series(
    val win: Int = 0,
    val loss: Int = 0
)
