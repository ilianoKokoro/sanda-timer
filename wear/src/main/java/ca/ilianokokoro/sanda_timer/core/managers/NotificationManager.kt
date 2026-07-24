package ca.ilianokokoro.sanda_timer.core.managers

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.wear.ongoing.OngoingActivity
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import ca.ilianokokoro.sanda_timer.core.receivers.DismissReceiver
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlin.math.abs
import android.app.NotificationManager as AndroidNotificationManager
import ca.ilianokokoro.sanda_timer.core.R as RCore

object NotificationManager {
    private lateinit var androidNotificationManager: AndroidNotificationManager

    fun init(context: Context) {
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
                    vibrationPattern = it.vibrationPattern

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
    }


    fun showTimerDoneNotification(
        context: Context,
        timerId: Long
    ) {
        val dismissIntent = PendingIntent.getBroadcast(
            context,
            timerId.hashCode(),
            Intent(context, DismissReceiver::class.java).apply {
                putExtra(Constants.TimerReceiver.TIMER_ID, timerId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = getBaseNotification(context, NotificationChannels.TIMER_DONE)
            .setSmallIcon(RCore.drawable.ic_timer)
            .setContentTitle(context.getString(RCore.string.timer_finished))
            .setContentIntent(IntentHelper.openAppPendingIntent(context))
            .setFullScreenIntent(IntentHelper.openAppPendingIntent(context), true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(false)
            .setAutoCancel(true)
            .addAction(
                NotificationCompat.Action.Builder(
                    RCore.drawable.ic_timer,
                    context.getString(RCore.string.dismiss),
                    dismissIntent
                ).build()
            )
            .build()

        androidNotificationManager.notify(getNotificationID(timerId.toString()), notification)
    }

    fun startTimerOngoingNotification(
        context: Context,
        timer: Timer
    ) {
        val notificationId = getNotificationID(timer.id.toString())
        val openTimerIntent = IntentHelper.openTimerPendingIntent(context, timer.id)

        val notificationBuilder = getBaseNotification(context, NotificationChannels.TIMER_ONGOING)
            .setContentIntent(openTimerIntent)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN) {
            notificationBuilder.setRequestPromotedOngoing(true)
        } else {
            val ongoingActivity = OngoingActivity.Builder(
                context,
                notificationId,
                notificationBuilder
            )
                .setStaticIcon(RCore.drawable.ic_timer)
                .setTouchIntent(openTimerIntent)
                .build()
            ongoingActivity.apply(context)
        }

        androidNotificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun stopTimerOngoingNotification(context: Context, timerId: Long) {
        val notificationId = getNotificationID(timerId.toString())
        androidNotificationManager.cancel(notificationId)
    }

    fun cancelTimerDoneNotification(timerId: Long) {
        androidNotificationManager.cancel(getNotificationID(timerId.toString()))
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
            nameRes = RCore.string.timer_ongoing_name,
            descriptionRes = RCore.string.timer_ongoing_description,
            importance = AndroidNotificationManager.IMPORTANCE_LOW,

            ),

        TIMER_DONE(
            channelId = "timer_done",
            nameRes = RCore.string.timer_done_name,
            descriptionRes = RCore.string.timer_done_description,
            importance = AndroidNotificationManager.IMPORTANCE_MAX,
            vibrationPattern = createTimerDoneVibrationPattern()
        );
    }

    private fun createTimerDoneVibrationPattern(): LongArray {
        val list = mutableListOf(0L)
        repeat(20) {
            list.add(1000L)
            list.add(500L)
        }
        return list.toLongArray()
    }
}