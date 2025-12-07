package com.app.project10.ui.components.calendar

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
import androidx.compose.material3.Text
import com.app.project10.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.project10.ui.theme.Pink80
import com.app.project10.ui.theme.PurpleGrey80
import com.app.project10.utils.TimeUtils.todayDateNow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SingleLineCalendar(modifier: Modifier = Modifier, onItemSelected: (LocalDate) -> Unit) {
    val today = todayDateNow
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
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PurpleGrey80)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "$formattedFirstDate - \n$formattedLastDate"
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .background(Pink80, CircleShape)
                        .size(32.dp),
                    onClick = {
                        currentWeekStart.value = currentWeekStart.value.minusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(Pink80, CircleShape)
                        .size(32.dp), onClick = {
                        currentWeekStart.value = currentWeekStart.value.plusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Pink80),
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = null
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

    val dayName = itemTitle.dayOfWeek.name.slice(0..2)
    val dayNumber = itemTitle.dayOfMonth.toString()

    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Pink80)
            .padding(4.dp)
            .clickable(onClick = {
                onItemSelected.invoke(itemTitle)
            })
            .size(height = 48.dp, width = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 12.sp,
            text = dayName,
        )
        Text(
            text = dayNumber
        )
    }
}