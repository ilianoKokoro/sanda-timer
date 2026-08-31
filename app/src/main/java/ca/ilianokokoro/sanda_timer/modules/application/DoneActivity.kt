package ca.ilianokokoro.sanda_timer.modules.application

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.done.DoneScreen
import ca.ilianokokoro.sanda_timer.ui.theme.SandaTimerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class DoneActivity : ComponentActivity() {
    private var timerId = -1L
    private var duration = Duration.ZERO
    private var vibrator: Vibrator? = null
    private var vibrationJob: Job? = null

    private val scope = CoroutineScope(Dispatchers.Main)

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

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        startVibrationLoop()

        setContent {
            SandaTimerTheme {
                DoneScreen(
                    duration = duration,
                    onDismiss = {
                        stopVibration()
                        cancelNotification()
                        finishAndRemoveTask()
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        stopVibration()
        super.onDestroy()
    }

    private fun startVibrationLoop() {
        vibrationJob = scope.launch {
            while (isActive) {
                vibrator?.let { v ->
                    if (v.hasVibrator()) {
                        @Suppress("DEPRECATION")
                        v.vibrate(
                            longArrayOf(
                                0L,
                                1000L, 1000L,
                                1000L, 1000L,
                                1000L, 1000L,
                                1000L, 1000L,
                                1000L
                            ),
                            -1 // repeat from start, -1 = don't repeat (we'll restart)
                        )
                    }
                }
                delay(8.seconds) // pause between pattern repeats before restarting
            }
        }
    }

    private fun stopVibration() {
        vibrationJob?.cancel()
        vibrationJob = null
        vibrator?.cancel()
    }

    private fun cancelNotification() {
        if (timerId != -1L) {
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(
                1000 + kotlin.math.abs(timerId.toString().hashCode() and 0x7fffffff)
            )
        }
    }
}
