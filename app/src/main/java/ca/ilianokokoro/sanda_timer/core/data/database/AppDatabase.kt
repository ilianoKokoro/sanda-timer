package ca.ilianokokoro.sanda_timer.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.ilianokokoro.sanda_timer.core.Constants
import java.util.concurrent.Executors


@Database(
    entities = [Song::class, PlaylistInfo::class, PlaylistSongCrossRef::class, Version::class],
    version = Constants.Database.VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, Constants.Database.NAME
            )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()

        /**
         * Utility method to run blocks on a dedicated background thread, used for io/database work.
         */
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }
}