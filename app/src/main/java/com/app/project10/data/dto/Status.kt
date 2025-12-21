package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val clock: String? = "",
    val halftime: Boolean = false,
    val short: Int = 0,
    val long: String? = ""
)
