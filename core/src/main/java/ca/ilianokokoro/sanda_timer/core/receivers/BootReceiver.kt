package ca.ilianokokoro.sanda_timer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.UserManagerCompat
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    companion object {
        var onBoot: (suspend () -> Unit)? = null
    }

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != Intent.ACTION_LOCKED_BOOT_COMPLETED && action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }

        // LOCKED_BOOT_COMPLETED can arrive while the device is still locked. The app's
        // database lives in credential-encrypted storage, which is not mounted until the
        // user unlocks, so opening it now fails with SQLITE_CANTOPEN_ENOENT. Defer the
        // cleanup to BOOT_COMPLETED, which is delivered once storage is available (or
        // immediately on devices without a lock screen).
        if (action == Intent.ACTION_LOCKED_BOOT_COMPLETED && !UserManagerCompat.isUserUnlocked(context)) {
            LogHelper.printd("Storage locked at boot, deferring timer cleanup")
            return
        }

        scope.launch {
            LogHelper.printd("Received boot event, deleting all timers")
            onBoot?.invoke() ?: LogHelper.printe("BootReceiver.onBoot callback not set")
        }
    }
}
