package ca.ilianokokoro.sanda_timer.core.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.provider.Settings
import androidx.core.app.NotificationCompat
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.helpers.VibrationHelper
import ca.ilianokokoro.sanda_timer.modules.application.TimerDoneActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerExpiredReceiver : BroadcastReceiver() {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)

        if (timerId == -1L) {
            LogHelper.printd("Missing timerId")
            return
        }

        LogHelper.printd("Timer $timerId finished")

        VibrationHelper.startTimerVibration(context)

        showFullScreenNotification(context, timerId)

        scope.launch {
            TimerRepository(context).deleteTimerById(timerId)
        }
    }

    private fun showFullScreenNotification(context: Context, timerId: Long) { // TEMP
        val channelId = "sanda_timer_expired_v3"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                "Timer Expired",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Shows a full-screen alert when a timer expires"
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI, audioAttributes)
            }
        )

        val activityIntent = Intent(context, TimerDoneActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            timerId.toInt(),
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(ca.ilianokokoro.sanda_timer.R.drawable.ic_alarm_notification)
            .setContentTitle("Timer Done!")
            .setContentText("Tap to dismiss")
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(false)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(timerId.toInt(), notification)
    }
}
