package ca.ilianokokoro.sanda_timer.core.helpers

import android.content.Context
import android.os.Build
import android.os.Handler
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

        if (!vibrator.hasVibrator()) {
            return
        }

        val effect = VibrationEffect.createWaveform(
            longArrayOf(0, 400, 200, 400, 200, 400),
            0,
        )

        vibrator.vibrate(effect)

        Handler(context.mainLooper).postDelayed(
            { vibrator.cancel() },
            Constants.MAX_VIBRATION_TIME.inWholeMilliseconds
        )
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