package ca.ilianokokoro.sanda_timer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.ilianokokoro.sanda_timer.core.Constants
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

@Entity(tableName = Constants.Database.TIMER_TABLE)
data class Timer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val duration: Duration = Duration.ZERO,
    val endTime: Instant? = null,
    val running: Boolean = false,
    val pausedRemaining: Duration = Duration.ZERO,
) {
    fun remainingDuration(): Duration {
        return if (running && endTime != null) {
            (endTime - Clock.System.now())
                .coerceAtLeast(Duration.ZERO)
        } else {
            pausedRemaining
        }
    }

}