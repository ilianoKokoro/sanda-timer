package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.R
import ca.ilianokokoro.sanda_timer.core.toFormattedDuration
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.isActive
import kotlin.time.Clock

@Composable
fun TimerPill(
    timer: Timer,
    onOpenTimer: () -> Unit,
    modifier: Modifier = Modifier,
    transformation: SurfaceTransformation? = null,
) {
    var progress by remember { mutableFloatStateOf(timer.percentFinished(Clock.System.now())) }
    var remainingText by remember {
        mutableStateOf(timer.remainingDuration(Clock.System.now()).toFormattedDuration())
    }
    var lastSecond by remember { mutableLongStateOf(-1L) }

    LaunchedEffect(timer) {
        while (isActive) { // TODO : check if this needs a rework
            withFrameNanos { }
            val now = Clock.System.now()
            progress = timer.percentFinished(now)

            val nowSecond = now.epochSeconds
            if (nowSecond != lastSecond) {
                lastSecond = nowSecond
                remainingText = timer.remainingDuration(now).toFormattedDuration()
            }

            if (progress >= 1f) {
                break
            }
        }
    }

    Card(
        onClick = onOpenTimer,
        modifier = modifier,
        transformation = transformation,
        shape = RoundedCornerShape(percent = 100),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(40.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = remainingText,
                    style = MaterialTheme.typography.numeralExtraSmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
                Text(
                    text = if (timer.running) {
                        timer.duration.toFormattedDuration()
                    } else {
                        stringResource(R.string.paused)
                    },
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}