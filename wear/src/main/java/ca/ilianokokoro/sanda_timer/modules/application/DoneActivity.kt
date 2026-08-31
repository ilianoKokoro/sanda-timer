package ca.ilianokokoro.sanda_timer.modules.application

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.wear.compose.material3.AppScaffold
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.modules.application.theme.SandaTimerTheme
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.done.DoneScreen
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class DoneActivity : ComponentActivity() {
    private var timerId = -1L
    private var duration = Duration.ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogHelper.printd("DoneActivity onCreate started")

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)
        duration = intent.getLongExtra(Constants.TimerReceiver.DURATION_SECONDS, 0L).seconds
        LogHelper.printd("DoneActivity timerId: $timerId, duration: $duration")
        
        enableEdgeToEdge()
        setContent {
            SandaTimerTheme {
                AppScaffold {
                    DoneScreen(
                        duration = duration,
                        onDismiss = {
                            cancelNotificationAndFinish()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LogHelper.printd("DoneActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        LogHelper.printd("DoneActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogHelper.printd("DoneActivity onDestroy, finishing: $isFinishing")
        if (isFinishing) {
            cancelNotification()
        }
    }

    private fun cancelNotificationAndFinish() {
        cancelNotification()
        finishAndRemoveTask()
    }

    private fun cancelNotification() {
        if (timerId != -1L) {
            NotificationManager.cancelTimerDoneNotification(timerId)
        }
    }
}
