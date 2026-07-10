package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.models.Timer

@Composable
fun TimerPill(
    timer: Timer,
    modifier: Modifier = Modifier,
    transformation: SurfaceTransformation? = null,
) {
    Card(
        onClick = { /* navigate to timer detail, or no-op if not needed */ },
        modifier = modifier,
        transformation = transformation,
    ) {
        Text(timer.remainingDuration().toString())
    }
}