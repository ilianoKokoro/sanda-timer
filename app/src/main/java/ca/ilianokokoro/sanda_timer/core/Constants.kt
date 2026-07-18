package ca.ilianokokoro.sanda_timer.core

import kotlin.time.Duration.Companion.seconds

object Constants {
    val UPDATE_DELAY = 1.seconds

    object DefaultValues {
        const val OFFSET = true
    }

    object TimeOffsets {
        const val SECURITY_MINUTES = 15L
    }

    object Strings {
        const val DURATION_FORMAT = "%s%02d:%02d:%02d"
    }

    object Vibrations {
        const val TICK_AMPLITUDE = 50
        const val SMALL_DURATION = 1L
    }
}