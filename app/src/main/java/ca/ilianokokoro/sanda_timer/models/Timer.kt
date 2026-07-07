package ca.ilianokokoro.sanda_timer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.ilianokokoro.sanda_timer.core.Constants
import java.time.Duration
import java.time.Instant

@Entity(tableName = Constants.Database.TIMER_TABLE)
data class Timer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val duration: Duration,
    val endTime: Instant?,
    val remaining: Duration,
    val running: Boolean
)