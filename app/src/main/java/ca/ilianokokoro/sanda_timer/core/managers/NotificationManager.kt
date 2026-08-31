package ca.ilianokokoro.sanda_timer.core.managers

import android.app.NotificationChannel
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import android.app.NotificationManager as AndroidNotificationManager
import ca.ilianokokoro.sanda_timer.core.R as RCore

object NotificationManager {

    private lateinit var androidNotificationManager: AndroidNotificationManager

    fun init(context: Context) {
        androidNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager

        NotificationChannels.entries.forEach { channel ->
            val notificationChannel = NotificationChannel(
                channel.channelId,
                context.getString(channel.nameRes),
                channel.importance
            ).apply {
                description = context.getString(channel.descriptionRes)

                channel.vibrationPattern?.let {
                    enableVibration(true)
                    vibrationPattern = it
                }
            }

            androidNotificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getBaseNotification(
        context: Context,
        channel: NotificationChannels
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channel.channelId)
    }

    fun showTimerDoneNotification(
        context: Context,
        timerId: Long
    ) {
        val durationSeconds = runBlocking {
            TimerRepository(context).getTimerById(timerId)?.duration?.inWholeSeconds ?: 0L
        }

        val notification = getBaseNotification(
            context,
            NotificationChannels.TIMER_DONE
        )
            .setContentIntent(
                IntentHelper.openDonePendingIntent(context, timerId, durationSeconds)
            )
            .setFullScreenIntent(
                IntentHelper.openDonePendingIntent(context, timerId, durationSeconds),
                true
            )
            .setSmallIcon(RCore.drawable.ic_timer)
            .setContentTitle(context.getString(RCore.string.timer_finished))
            .setContentText(context.getString(RCore.string.tap_to_open))
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        androidNotificationManager.notify(
            getNotificationID(timerId.toString()),
            notification.build()
        )
        LogHelper.printd("Timer done notification shown for timer $timerId (duration=${durationSeconds}s)")
    }

    fun createTimerNotification(
        context: Context,
        timer: Timer
    ): NotificationCompat.Builder {
        return getBaseNotification(
            context,
            NotificationChannels.TIMER_ONGOING
        )
            .setContentIntent(IntentHelper.openTimerPagePendingIntent(context))
            .setSmallIcon(RCore.drawable.ic_timer)
            .setContentTitle(context.getString(RCore.string.timer_running))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setWhen(timer.endTime!!.toEpochMilliseconds())
            .setUsesChronometer(true)
            .setChronometerCountDown(true)
            .setRequestPromotedOngoing(true)
    }

    fun updateTimerNotification(
        context: Context,
        timer: Timer
    ) {
        androidNotificationManager.notify(
            getNotificationID(timer.id.toString()),
            createTimerNotification(context, timer).build()
        )
    }

    fun stopTimerNotification(timerId: Long) {
        androidNotificationManager.cancel(
            getNotificationID(timerId.toString())
        )
    }

    fun cancelTimerDoneNotification(timerId: Long) {
        androidNotificationManager.cancel(
            getNotificationID(timerId.toString())
        )
    }

    private fun getNotificationID(id: String): Int {
        return 1000 + abs(id.hashCode() and 0x7fffffff)
    }

    private enum class NotificationChannels(
        val channelId: String,
        @StringRes val nameRes: Int,
        @StringRes val descriptionRes: Int,
        val importance: Int,
        val vibrationPattern: LongArray? = null
    ) {

        TIMER_ONGOING(
            channelId = "timer_ongoing",
            nameRes = RCore.string.timer_ongoing_name,
            descriptionRes = RCore.string.timer_ongoing_description,
            importance = AndroidNotificationManager.IMPORTANCE_LOW
        ),

        TIMER_DONE(
            channelId = "timer_done",
            nameRes = RCore.string.timer_done_name,
            descriptionRes = RCore.string.timer_done_description,
            importance = AndroidNotificationManager.IMPORTANCE_MAX,
            vibrationPattern = createTimerDoneVibrationPattern()
        )
    }

    private fun createTimerDoneVibrationPattern(): LongArray {
        return longArrayOf(
            0L,
            500L, 200L,
            500L, 200L,
            500L, 200L,
            500L, 200L,
            500L
        )
    }
}