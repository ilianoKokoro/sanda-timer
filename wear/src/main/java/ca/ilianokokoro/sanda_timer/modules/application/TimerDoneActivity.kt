package ca.ilianokokoro.sanda_timer.modules.application

import android.app.KeyguardManager
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import ca.ilianokokoro.sanda_timer.core.Constants

class TimerDoneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setShowWhenLocked(true)
        setTurnScreenOn(true)

        val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(this, null)

        val timerId = intent.getLongExtra(Constants.TimerReceiver.TIMER_ID, -1L)

        val layout = LinearLayout(this).apply { // TEMP
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            setBackgroundColor(Color.BLACK)
        }

        val title = TextView(this).apply {
            text = "Timer Done!"
            setTextColor(Color.WHITE)
            textSize = 24f
            gravity = Gravity.CENTER
        }

        val dismiss = Button(this).apply {
            text = "Dismiss"
            setOnClickListener {
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(timerId.toInt())
                finish()
            }
        }

        layout.addView(title)
        layout.addView(dismiss)
        setContentView(layout)
    }
}
