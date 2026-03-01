package com.app.project10.presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.project10.R
import com.app.project10.core.utils.DateTimeUtils.todayDate
import com.app.project10.presentation.theme.Dimens
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SingleLineCalendar(
    modifier: Modifier = Modifier,
    colors: SingleLineCalendarColors = SingleLineCalendarColors(),
    onItemSelected: (LocalDate) -> Unit
) {
    val dimens = Dimens.current
    val today = todayDate
    val initialWeekStart = today.minusDays((today.dayOfWeek.value - 1).toLong())
    var currentWeekStart by remember { mutableStateOf(initialWeekStart) }
    var selectedDate by remember { mutableStateOf(today) }

    val weekToShow =
        remember(currentWeekStart) { List(7) { currentWeekStart.plusDays(it.toLong()) } }
    val formatter = remember { DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH) }
    val formattedRange = remember(weekToShow) {
        "${weekToShow.first().format(formatter)} - ${weekToShow.last().format(formatter)}"
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.cardCorner))
            .background(colors.containerColor)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.xs)
                .height(dimens.calendarHeaderHeight)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = formattedRange,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.defaultTextColor
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .background(colors.navButtonColor, CircleShape)
                        .size(dimens.calendarNavButton),
                    onClick = {
                        currentWeekStart = currentWeekStart.minusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier.size(dimens.calendarNavIcon),
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "Previous week",
                        tint = colors.navIconColor
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(start = dimens.xs)
                        .background(colors.navButtonColor, CircleShape)
                        .size(dimens.calendarNavButton), onClick = {
                        currentWeekStart = currentWeekStart.plusWeeks(1)
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(dimens.calendarNavIcon),
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = "Next week",
                        tint = colors.navIconColor
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = dimens.xs),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(
                count = weekToShow.size,
                key = { index -> weekToShow[index].toEpochDay() }
            ) { index ->
                val date = weekToShow[index]
                CalendarItem(
                    itemDate = date,
                    isSelected = date == selectedDate,
                    defaultBackground = colors.dayDefaultColor,
                    selectedBackground = colors.daySelectedColor,
                    defaultTextColor = colors.defaultTextColor,
                    selectedTextColor = colors.selectedTextColor,
                    borderColor = colors.borderColor
                ) {
                    selectedDate = date
                    onItemSelected(date)
                }
            }
        }
    }
}

data class SingleLineCalendarColors(
    val containerColor: Color = Color.Transparent,
    val navButtonColor: Color = Color.Transparent.copy(alpha = 0.18f),
    val navIconColor: Color = Color.Black.copy(alpha = 0.62f),
    val dayDefaultColor: Color = Color.Transparent.copy(alpha = 0.10f),
    val daySelectedColor: Color = Color.Transparent.copy(alpha = 0.26f),
    val defaultTextColor: Color = Color.Black.copy(alpha = 0.65f),
    val selectedTextColor: Color = Color.Black.copy(alpha = 0.90f),
    val borderColor: Color = Color.Black.copy(alpha = 0.20f),
)

@Composable
private fun CalendarItem(
    itemDate: LocalDate,
    isSelected: Boolean,
    defaultBackground: Color,
    selectedBackground: Color,
    defaultTextColor: Color,
    selectedTextColor: Color,
    borderColor: Color,
    onItemSelected: () -> Unit
) {
    val dimens = Dimens.current
    val dayName = remember(itemDate) {
        itemDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(Locale.ENGLISH)
    }
    val dayNumber = itemDate.dayOfMonth.toString()
    val backgroundColor = if (isSelected) selectedBackground else defaultBackground
    val textColor = if (isSelected) selectedTextColor else defaultTextColor

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(dimens.xs))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(dimens.xs))
            .padding(dimens.xxs)
            .clickable(onClick = onItemSelected)
            .size(height = dimens.calendarDayHeight, width = dimens.calendarDayWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = dayName,
            color = textColor
        )
        Text(
            text = dayNumber,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

