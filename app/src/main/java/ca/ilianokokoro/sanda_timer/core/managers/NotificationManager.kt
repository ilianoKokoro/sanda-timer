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
        val notification = getBaseNotification(context, NotificationChannels.TIMER_DONE)
            .setSmallIcon(R.drawable.ic_alarm_notification)
            .setContentTitle("Timer Done!") // TEMP
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


        val notificationBuilder = // TEMP
            getBaseNotification(context, NotificationChannels.TIMER_ONGOING)
                .setContentTitle("Always On Service")
                .setContentText("Service is running in background")
                .setSmallIcon(android.R.drawable.ic_lock_lock)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setWhen(timer.endTime?.toEpochMilliseconds() ?: 0)
                .setUsesChronometer(true)
                .setChronometerCountDown(true)
                .setRequestPromotedOngoing(true)

        val notificationId = getNotificationID(timer.id.toString())

//            val ongoingActivity =
//                OngoingActivity.Builder(context, notificationId, notificationBuilder)
//                    // Sets the icon that appears on the watch face in active mode.
//                    .setAnimatedIcon(R.drawable.ic_alarm_notification)
//                    // Sets the icon that appears on the watch face in ambient mode.
//                    .setStaticIcon(android.R.drawable.ic_menu_manage)
//                    // Sets the tap target to bring the user back to the app.
//                    .setTouchIntent(pendingIntent)
//                    .build()
//
//            ongoingActivity.apply(context)

        androidNotificationManager.notify(notificationId, notificationBuilder.build())


    }


    private fun getNotificationID(id: String): Int {
        return 1000 + abs(id.hashCode() and 0x7fffffff)
    }


    private enum class NotificationChannels(
        val channelId: String,
        @param:StringRes val nameRes: Int,
        @param:StringRes val descriptionRes: Int,
        val importance: Int,
    ) {

        TIMER_ONGOING(
            channelId = "timer_ongoing",
            nameRes = R.string.timer_ongoing_name,
            descriptionRes = R.string.timer_ongoing_description,
            importance = AndroidNotificationManager.IMPORTANCE_DEFAULT,
        ),

        TIMER_DONE(
            channelId = "timer_done",
            nameRes = R.string.timer_done_name,
            descriptionRes = R.string.timer_done_description,
            importance = AndroidNotificationManager.IMPORTANCE_MAX,
        );
    }
}