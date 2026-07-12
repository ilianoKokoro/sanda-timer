package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerExpiredReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timerId = intent.getLongExtra("timerId", -1L)

        if (timerId == -1L) {
            LogHelper.printd("Missing timerId")
            return
        }

        LogHelper.printd("Timer $timerId expired")

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val timerDataSource = AppDatabase
                    .getInstance(context)
                    .timerDataSource()

                timerDataSource.deleteById(timerId)

            } finally {
                pendingResult.finish()
            }
        }
    }
}