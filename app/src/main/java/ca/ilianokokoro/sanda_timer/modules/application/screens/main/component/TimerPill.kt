package ca.ilianokokoro.sanda_timer.modules.application.screens.main.component

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.models.Timer

@Composable
fun TimerPill(timer: Timer) {
    Text(timer.remainingDuration().toString())
}