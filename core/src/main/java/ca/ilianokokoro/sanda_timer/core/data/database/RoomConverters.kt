package ca.ilianokokoro.sanda_timer.core.data.database

import androidx.room.TypeConverter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

class RoomConverters {

    @TypeConverter
    fun instantToLong(value: Instant?): Long? =
        value?.toEpochMilliseconds()

    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun durationToLong(value: Duration): Long =
        value.inWholeMilliseconds

    @TypeConverter
    fun longToDuration(value: Long): Duration =
        value.milliseconds
}
