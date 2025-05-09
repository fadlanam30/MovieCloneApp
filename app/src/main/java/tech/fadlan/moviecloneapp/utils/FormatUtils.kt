package tech.fadlan.moviecloneapp.utils

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatMinutesToHourMinute(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "${hours}h ${remainingMinutes}m"
}

fun formatLocalDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(dateString, formatter)
}

fun formatToThousands(number: Int): String {
    return if (number >= 1000) {
        String.format(Locale.US, "%.1fk", number / 1000.0)
    } else {
        number.toString()
    }
}

fun formatToUSDateString(isoDate: String): String {
    if (isoDate.isEmpty())
        return isoDate
    val zonedDateTime = ZonedDateTime.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.US)
    return zonedDateTime.format(formatter)
}