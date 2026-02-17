package com.app.project10.presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.app.project10.R
import com.app.project10.core.utils.DateTimeUtils.todayDate
import com.app.project10.presentation.theme.Dimens
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SingleLineCalendar(modifier: Modifier = Modifier, onItemSelected: (LocalDate) -> Unit) {
    val dimens = Dimens.current
    val today = todayDate
    val startOfWeek = today.minusDays((today.dayOfWeek.value - 1).toLong())
    val currentWeekStart = remember { mutableStateOf(startOfWeek) }

    val weekToShow = remember(currentWeekStart.value) {
        mutableStateListOf<LocalDate>().apply {
            clear()
            addAll(List(7) { currentWeekStart.value.plusDays(it.toLong()) })
        }
    }

    val formatter = DateTimeFormatter.ofPattern("dd, MMM, yyyy", Locale.ENGLISH)

    val formattedFirstDate = weekToShow[0].format(formatter)
    val formattedLastDate = weekToShow[6].format(formatter)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.cardCorner))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(dimens.xs)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(dimens.xs)
                .height(dimens.calendarHeaderHeight)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "$formattedFirstDate - \n$formattedLastDate",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .size(dimens.calendarNavButton),
                    onClick = {
                        currentWeekStart.value = currentWeekStart.value.minusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier.size(dimens.calendarNavIcon),
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(start = dimens.xs)
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .size(dimens.calendarNavButton), onClick = {
                        currentWeekStart.value = currentWeekStart.value.plusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(dimens.calendarNavIcon),
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(7) { index ->
                CalendarItem(itemTitle = weekToShow[index], onItemSelected)
            }
        }
    }
}

@Composable
private fun CalendarItem(itemTitle: LocalDate, onItemSelected: (LocalDate) -> Unit) {
    val dimens = Dimens.current

    val dayName = itemTitle.dayOfWeek.name.slice(0..2)
    val dayNumber = itemTitle.dayOfMonth.toString()

    Column(
        modifier = Modifier
            .padding(dimens.xxs)
            .clip(RoundedCornerShape(dimens.xs))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(dimens.xxs)
            .clickable(onClick = {
                onItemSelected.invoke(itemTitle)
            })
            .size(height = dimens.calendarDayHeight, width = dimens.calendarDayWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = dayName,
        )
        Text(
            text = dayNumber,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


