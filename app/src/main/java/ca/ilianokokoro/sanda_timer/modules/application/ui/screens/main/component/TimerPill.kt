package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlin.time.Instant

@Composable
fun TimerPill(
    timer: Timer,
    now: Instant,
    modifier: Modifier = Modifier,
    transformation: SurfaceTransformation? = null,
) {
    Card(
        onClick = { /* TEMP */ },
        modifier = modifier,
        transformation = transformation,
    ) {
        Text(timer.remainingDuration(now).toString())
    }
}