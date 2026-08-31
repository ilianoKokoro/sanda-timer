package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.done

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.EdgeButtonSize
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.core.toFormattedDuration
import ca.ilianokokoro.sanda_timer.core.withCenteredColons
import kotlin.time.Duration

@Composable
fun DoneScreen(
    duration: Duration,
    onDismiss: () -> Unit,
) {
    ScreenScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.timer_finished),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = duration.toFormattedDuration()
                        .withCenteredColons(style = MaterialTheme.typography.bodyLarge),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            EdgeButton(
                onClick = onDismiss,
                buttonSize = EdgeButtonSize.Small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            ) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = Icons.Rounded.Check.name,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
            }
        }
    }
}
