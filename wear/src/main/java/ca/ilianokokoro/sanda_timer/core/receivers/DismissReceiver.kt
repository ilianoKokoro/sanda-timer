package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.core.Constants
import kotlin.math.abs

class DismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)
        if (timerId != -1L) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            nm.cancel(getNotificationId(timerId))
        }
    }

    private fun getNotificationId(timerId: Long): Int =
        1000 + abs(timerId.toString().hashCode() and 0x7fffffff)
}
