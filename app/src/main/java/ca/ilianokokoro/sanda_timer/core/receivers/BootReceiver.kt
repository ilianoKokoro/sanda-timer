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
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            return
        }

        scope.launch {
            LogHelper.printd("Received boot event, deleting all timers")
            TimerRepository(context).clearTimers()
        }
    }
}