package ca.ilianokokoro.sanda_timer.modules.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material3.AppScaffold
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.modules.application.theme.SandaTimerTheme
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.done.DoneScreen

class DoneActivity : ComponentActivity() {
    private var timerId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)
        setContent {
            SandaTimerTheme {
                AppScaffold {
                    DoneScreen(
                        onDismiss = {
                            cancelNotificationAndFinish()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
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
