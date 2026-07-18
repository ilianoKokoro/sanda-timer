package ca.ilianokokoro.sanda_timer.core.data.datasources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
interface TimerDataSource {
    @Query("SELECT * FROM timers")
    fun getAllFlow(): Flow<List<Timer>>

    @Query("SELECT * FROM timers WHERE id = :id")
    fun getFlow(id: Long): Flow<Timer?>

    @Query("SELECT * FROM timers")
    suspend fun getAll(): List<Timer>

    @Query("SELECT * FROM timers WHERE id = :id")
    suspend fun get(id: Long): Timer?

    @Insert
    suspend fun insert(timer: Timer): Long

    @Update
    suspend fun update(timer: Timer)

    @Delete
    suspend fun delete(timer: Timer)

    @Query("DELETE FROM timers WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM timers")
    suspend fun deleteAll()

    @Query("DELETE FROM timers WHERE endTime <= :now")
    suspend fun deleteExpired(now: Instant)
}
