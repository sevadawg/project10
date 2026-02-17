package com.app.project10.data.dto.game


data class ScoresDetails(
    val win: Int = 0,
    val loss: Int = 0,
    val series: Series,
    val linescore: List<String> = listOf<String>(),
    val points: Int = 0
)

