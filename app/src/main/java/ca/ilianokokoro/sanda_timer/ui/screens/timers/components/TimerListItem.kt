package ca.ilianokokoro.sanda_timer.ui.screens.timers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.core.toFormattedDuration
import ca.ilianokokoro.sanda_timer.core.withCenteredColons
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.isActive
import kotlin.time.Clock

// TODO redo the element optimized for phones
@Composable
fun TimerListItem(
    timer: Timer,
    onOpenTimer: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var progress by remember { mutableFloatStateOf(timer.percentFinished(Clock.System.now())) }
    var remainingText by remember {
        mutableStateOf(timer.remainingDuration(Clock.System.now()).toFormattedDuration())
    }
    var lastSecond by remember { mutableLongStateOf(-1L) }


    LaunchedEffect(timer) {
        if (!timer.running) {
            return@LaunchedEffect
        }

        while (isActive) {
            withFrameNanos { }

            val now = Clock.System.now()

            progress = timer.percentFinished(now)

            if (now.epochSeconds != lastSecond) {
                lastSecond = now.epochSeconds
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
        shape = RoundedCornerShape(24.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(56.dp),
                    strokeWidth = 5.dp,
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = remainingText.withCenteredColons(MaterialTheme.typography.headlineMedium),
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = if (timer.running) {
                        timer.duration.toFormattedDuration()
                    } else {
                        stringResource(R.string.paused)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            FilledIconButton(
                shapes = IconButtonDefaults.shapes(),
                onClick = onCancel,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = Icons.Rounded.Close.name
                )
            }
        }
    }
}