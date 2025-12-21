package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Periods(
    val current: Int = 0,
    val total: Int = 0,
    val endOfPeriod: Boolean = false
)
