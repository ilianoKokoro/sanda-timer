package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LogHelper.printd(intent.action.toString())
        if (intent.action != Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            return
        }
        LogHelper.printd("Received boot event")

        CoroutineScope(Dispatchers.IO).launch {
            val timerRepository = TimerRepository(context)
            LogHelper.printd("Deleting all timers")
            timerRepository.clearTimers()
        }
    }
}