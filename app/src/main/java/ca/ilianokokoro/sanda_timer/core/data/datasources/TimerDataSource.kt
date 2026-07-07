package ca.ilianokokoro.sanda_timer.core.data.datasources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDataSource {
    @Query("SELECT * FROM timers")
    suspend fun getAll(): Flow<List<Timer>>

    @Query("SELECT * FROM timers WHERE id = :id")
    suspend fun get(id: Long): Timer?

    @Insert
    suspend fun insert(timer: Timer): Long

    @Update
    suspend fun update(timer: Timer)

    @Delete
    suspend fun delete(timer: Timer)
}