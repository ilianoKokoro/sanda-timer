package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.helpers.VibrationHelper
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
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

        NotificationManager.showTimerDoneNotification(context, timerId)

        scope.launch {
            TimerRepository(context).deleteTimerById(timerId)
        }
    }
}
