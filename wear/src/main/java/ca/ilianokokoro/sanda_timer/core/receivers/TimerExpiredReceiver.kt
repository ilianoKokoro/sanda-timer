package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.modules.application.DoneActivity
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
        NotificationManager.showTimerDoneNotification(context, timerId)

        val durationSeconds = runBlocking {
            TimerRepository(context).getTimerById(timerId)?.duration?.inWholeSeconds ?: 0L
        }

        Handler(Looper.getMainLooper()).post {
            try {
                context.startActivity(
                    Intent(context, DoneActivity::class.java).apply {
                        putExtra(Constants.TimerReceiver.TIMER_ID, timerId)
                        putExtra(
                            Constants.TimerReceiver.DURATION_SECONDS,
                            durationSeconds,
                        )
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                )
                LogHelper.printd("DoneActivity launched successfully")
            } catch (e: Exception) {
                LogHelper.printe("Failed to launch DoneActivity: $e")
            }
        }

        scope.launch {
            TimerRepository(context).deleteTimerById(timerId)
        }
    }
}
