package ca.ilianokokoro.sanda_timer.core.managers

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import ca.ilianokokoro.sanda_timer.R
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlin.math.abs
import android.app.NotificationManager as AndroidNotificationManager
import ca.ilianokokoro.sanda_timer.core.R as RCore

object NotificationManager {
    private lateinit var androidNotificationManager: AndroidNotificationManager
    private lateinit var pendingIntent: PendingIntent

    fun init(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        androidNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager

        NotificationChannels.entries.forEach {
            val notificationChannel = NotificationChannel(
                it.channelId,
                context.getString(it.nameRes),
                it.importance
            ).apply {
                description = context.getString(it.descriptionRes)

                if (it.vibrationPattern != null) {
                    enableVibration(true)
                    vibrationPattern = longArrayOf(
                        0,    // delay
                        1000, // vibrate 1 second
                        500,  // pause
                        1000, // vibrate 1 second
                        500,  // pause
                        1000,  // vibrate 1 second
                        500,  // pause
                        1000, // vibrate 1 second
                        500,  // pause
                        1000, // vibrate 1 second
                        500,  // pause
                        1000  // vibrate 1 second
                    )
                }
            }
            androidNotificationManager.createNotificationChannel(notificationChannel)
        }

    }


    private fun getBaseNotification(
        context: Context,
        channel: NotificationChannels
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            channel.channelId
        )
            .setContentIntent(pendingIntent)
    }


    fun showTimerDoneNotification(
        context: Context,
        timerId: Long
    ) {
        stopTimerOngoingNotification(context, timerId)
        val notification = getBaseNotification(context, NotificationChannels.TIMER_DONE)
            .setSmallIcon(RCore.drawable.ic_timer)
            .setContentTitle("Timer Finished") // TEMP
            .setContentText("Tap to dismiss") // TEMP
            // .setFullScreenIntent(pendingIntent, true) TODO
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(false)
            .setAutoCancel(true)
            .build()

        androidNotificationManager.notify(getNotificationID(timerId.toString()), notification)
    }

    fun startTimerOngoingNotification(
        context: Context,
        timer: Timer
    ) {
        val notificationId = getNotificationID(timer.id.toString())

        val notificationBuilder =
            getBaseNotification(context, NotificationChannels.TIMER_ONGOING)
                .setSmallIcon(RCore.drawable.ic_timer)
                .setContentTitle("Timer running")
                .setContentText("Timer is active")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setWhen(timer.endTime!!.toEpochMilliseconds())
                .setUsesChronometer(true)
                .setChronometerCountDown(true)
                .setRequestPromotedOngoing(true)

//        val ongoingActivity = OngoingActivity.Builder(
//            context,
//            notificationId,
//            notificationBuilder
//        )
//            .setStaticIcon(R.drawable.ic_timer)
//            .setTouchIntent(pendingIntent)
//            .build()
//
//        ongoingActivity.apply(context)

        androidNotificationManager.notify(
            notificationId,
            notificationBuilder.build()
        )
    }

    fun stopTimerOngoingNotification(context: Context, timerId: Long) {
        val notificationId = getNotificationID(timerId.toString())
        androidNotificationManager.cancel(notificationId)
    }

    private fun getNotificationID(id: String): Int {
        return 1000 + abs(id.hashCode() and 0x7fffffff)
    }


    private enum class NotificationChannels(
        val channelId: String,
        @param:StringRes val nameRes: Int,
        @param:StringRes val descriptionRes: Int,
        val importance: Int,
        val vibrationPattern: LongArray? = null
    ) {

        TIMER_ONGOING(
            channelId = "timer_ongoing",
            nameRes = R.string.timer_ongoing_name,
            descriptionRes = R.string.timer_ongoing_description,
            importance = AndroidNotificationManager.IMPORTANCE_LOW,

            ),

        TIMER_DONE(
            channelId = "timer_done",
            nameRes = R.string.timer_done_name,
            descriptionRes = R.string.timer_done_description,
            importance = AndroidNotificationManager.IMPORTANCE_MAX,
            vibrationPattern = longArrayOf(
                0,    // delay
                1000, // vibrate 1 second
                500,  // pause
                1000, // vibrate 1 second
                500,  // pause
                1000  // vibrate 1 second
            )
        );
    }
}