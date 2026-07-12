package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }
        LogHelper.printd("Received boot event")
        val timerDataSource = AppDatabase.getInstance(context).timerDataSource()

        CoroutineScope(Dispatchers.IO).launch {
            LogHelper.printd("Deleting all expired timers")
            timerDataSource.deleteExpired(Clock.System.now())
        }
    }
}