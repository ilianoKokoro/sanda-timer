package ca.ilianokokoro.sanda_timer.core

import java.util.Locale
import kotlin.time.Duration

/**
 * Formats a [Duration] as a string, always showing hours, with sign support.
 * Example outputs: "00:05:30", "-00:01:00", "01:15:45"
 */
fun Duration.toFormattedString(): String {
    val totalSeconds = inWholeSeconds
    val sign = if (totalSeconds < 0) {
        "-"
    } else {
        ""
    }

    val absoluteSeconds = kotlin.math.abs(totalSeconds)

    val hours = absoluteSeconds / 3600
    val minutes = (absoluteSeconds % 3600) / 60
    val seconds = absoluteSeconds % 60

    return Constants.Strings.DURATION_FORMAT.format(
        sign,
        hours,
        minutes,
        seconds
    )
}

/**
 * Formats a [Duration] as a string, omitting hours when zero, using [Locale.ROOT] formatting.
 * Example outputs: "05:30", "01:15:45"
 */
fun Duration.toFormattedDuration(): String {
    val totalSeconds = inWholeSeconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
    }
}

