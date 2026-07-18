package ca.ilianokokoro.sanda_timer.core.helpers

import android.util.Log

object LogHelper {
    const val TAG = "SandaTimer"

    fun printd(message: String, tag: String = TAG) {
        Log.d(tag, message)
    }

    fun printe(message: String, tag: String = TAG, exception: Exception? = null) {
        Log.e(TAG, message, exception)
    }
}
