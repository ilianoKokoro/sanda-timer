package ca.ilianokokoro.sanda_timer.core.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.receivers.TimerExpiredReceiver
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant


class TimerRepository(
    private val context: Context,
) {
    private val alarmManager =
        context.getSystemService(AlarmManager::class.java)
    private val timerDataSource = AppDatabase.getInstance(context).timerDataSource()

    suspend fun createTimer(duration: Duration): Long {
        val endTime = Clock.System.now() + duration
        val timer = Timer(endTime = endTime, duration = duration)

        val id = timerDataSource.insert(timer)

        scheduleAlarm(id, endTime)

        return id
    }

    suspend fun deleteTimer(timer: Timer) {
        cancelAlarm(timer.id)
        timerDataSource.delete(timer)
    }

    suspend fun deleteTimerById(id: Long) {
        cancelAlarm(id)
        timerDataSource.deleteById(id)
    }

    suspend fun deleteExpiredTimers() {
        timerDataSource.deleteExpired(Clock.System.now())
    }

    suspend fun clearTimers() {
        timerDataSource.deleteAll()
    }

    suspend fun getAllTimers(): List<Timer> =
        timerDataSource.getAll()

    private fun scheduleAlarm(
        timerId: Long,
        endTime: Instant
    ) {
        val intent = Intent(context, TimerExpiredReceiver::class.java).apply {
            putExtra(Constants.TimerReceiver.TIMER_ID, timerId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            timerId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()
        ) {
            return
        }

        LogHelper.printd("alarmManager.setExactAndAllowWhileIdle")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            endTime.toEpochMilliseconds(),
            pendingIntent
        )
    }

    private fun cancelAlarm(timerId: Long) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            timerId.toInt(),
            Intent(context, TimerExpiredReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
}
