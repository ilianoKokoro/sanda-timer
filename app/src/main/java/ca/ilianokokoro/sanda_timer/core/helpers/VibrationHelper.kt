package ca.ilianokokoro.sanda_timer.core.helpers

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlin.time.Duration.Companion.seconds

object VibrationHelper {
    private object Constants {
        val MAX_VIBRATION_TIME = 15.seconds
    }

    fun startTimerVibration(context: Context) {
        val vibrator = getVibrator(context)

        LogHelper.printd("hasVibrator=${vibrator.hasVibrator()}", tag = "TimerVibration")

        if (!vibrator.hasVibrator()) {
            LogHelper.printd("No vibrator", tag = "TimerVibration")
            return
        }

        LogHelper.printd("Starting vibration", tag = "TimerVibration")

        val effect = VibrationEffect.createWaveform(
            longArrayOf(0, 400, 200, 400, 200, 400),
            0,
        )

        vibrator.vibrate(effect)

        LogHelper.printd("Vibration requested", tag = "TimerVibration")
    }

    fun stopTimerVibration(context: Context) {
        val vibrator = getVibrator(context)

        if (!vibrator.hasVibrator()) {
            return
        }

        vibrator.cancel()

    }


    private fun getVibrator(context: Context): Vibrator {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        return vibrator
    }


}