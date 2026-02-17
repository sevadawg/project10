package com.app.project10.core.utils

import java.time.LocalDate

object DateTimeUtils {
    val todayDateString: String
        get() = LocalDate.now().toString()

    val todayDate: LocalDate
        get() = LocalDate.now()
}

