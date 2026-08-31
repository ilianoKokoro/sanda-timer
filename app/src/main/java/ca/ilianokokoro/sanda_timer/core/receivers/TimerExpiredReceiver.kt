package ca.ilianokokoro.sanda_timer.core.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.core.repositories.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TimerExpiredReceiver : BroadcastReceiver() {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)

        if (timerId == -1L) {
            LogHelper.printd("Missing timerId")
            return
        }

        LogHelper.printd("Timer $timerId finished")

        val pendingResult = goAsync()

        val durationSeconds = runBlocking {
            TimerRepository(context).getTimerById(timerId)?.duration?.inWholeSeconds ?: 0L
        }

        NotificationManager.showTimerDoneNotification(context, timerId)

        try {
            IntentHelper.openDonePendingIntent(context, timerId, durationSeconds).send()
            LogHelper.printd("DoneActivity PendingIntent sent successfully")
        } catch (e: PendingIntent.CanceledException) {
            LogHelper.printe("DoneActivity PendingIntent cancelled: $e")
        }

        scope.launch {
            TimerRepository(context).deleteTimerById(timerId)
            pendingResult.finish()
        }
    }
}
