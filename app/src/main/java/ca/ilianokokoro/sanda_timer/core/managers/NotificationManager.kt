package ca.ilianokokoro.sanda_timer.core.managers

import android.app.NotificationChannel
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import ca.ilianokokoro.sanda_timer.models.Timer
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
        val notification = getBaseNotification(
            context,
            NotificationChannels.TIMER_DONE
        )
            .setContentIntent(IntentHelper.openAppPendingIntent(context))
            .setSmallIcon(RCore.drawable.ic_timer)
            .setContentTitle(context.getString(RCore.string.timer_finished))
            .setContentText("Tap to open")
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)


        androidNotificationManager.notify(
            getNotificationID(timerId.toString()),
            notification.build()
        )
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
            importance = AndroidNotificationManager.IMPORTANCE_HIGH,
            vibrationPattern = createTimerDoneVibrationPattern()
        )
    }

    private fun createTimerDoneVibrationPattern(): LongArray {
        val pattern = mutableListOf(0L)
        repeat(20) {
            pattern += 1000L
            pattern += 500L
        }
        return pattern.toLongArray()
    }
}