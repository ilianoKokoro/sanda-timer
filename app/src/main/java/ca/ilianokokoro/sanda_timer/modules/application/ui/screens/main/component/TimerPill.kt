package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.core.extensions.toFormattedString
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
        shape = RoundedCornerShape(percent = 100),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = timer.remainingDuration(now).toFormattedString(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1,
            )
            Text(
                text = timer.duration.toFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
