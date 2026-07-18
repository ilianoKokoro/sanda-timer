package ca.ilianokokoro.sanda_timer.core

import android.content.Context
import android.text.format.DateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

fun LocalTime.toFormattedString(
    context: Context
): String {
    val pattern = if (DateFormat.is24HourFormat(context)) {
        "HH:mm"
    } else {
        "h:mm a"
    }

    return format(
        DateTimeFormatter.ofPattern(pattern)
    )
}

fun LocalTime.durationBetween(other: LocalTime): Duration {
    return java.time.Duration.between(this, other).toMillis().milliseconds
}
