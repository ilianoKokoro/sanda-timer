package ca.ilianokokoro.sanda_timer.models

import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.durationBetween
import java.time.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days


data class Calculation(
    var applyOffset: Boolean = Constants.DefaultValues.OFFSET,
    private var _targetTime: LocalTime = LocalTime.now()
) {
    fun setTargetTime(hours: Int, minutes: Int) {
        _targetTime = LocalTime.of(hours, minutes)
    }

    fun getTargetTime(): LocalTime {
        return _targetTime
    }

    val duration: Duration
        get() {
            var currentTime = LocalTime.now()

            if (applyOffset) {
                currentTime = currentTime.plusMinutes(Constants.TimeOffsets.SECURITY_MINUTES)
            }

            var result = currentTime.durationBetween(_targetTime)

            if (_targetTime.isBefore(currentTime)) {
                result += 1.days
            }

            return result
        }
}