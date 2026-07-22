package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.details

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material3.CircularProgressIndicatorDefaults
import androidx.wear.compose.material3.IconButtonDefaults
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import ca.ilianokokoro.sanda_timer.core.toFormattedDuration
import ca.ilianokokoro.sanda_timer.models.Timer
import ca.ilianokokoro.sanda_timer.modules.application.ui.components.MaterialUButton
import ca.ilianokokoro.sanda_timer.modules.application.ui.components.MaterialUToggleButton
import kotlinx.coroutines.isActive
import kotlin.time.Clock

@Composable
fun DetailsScreen(
    timer: Timer,
    onBack: () -> Unit,
    application: Application,
    detailsViewModel: DetailsViewModel = viewModel(
        factory =
            DetailsViewModel.Factory(application = application, timer = timer)
    )
) {
    val uiState = detailsViewModel.uiState.collectAsStateWithLifecycle().value
    val timer = uiState.timer

    var remainingText by remember {
        mutableStateOf(timer.remainingDuration(Clock.System.now()).toFormattedDuration())
    }

    var progress by remember { mutableFloatStateOf(timer.percentFinished(Clock.System.now())) }
    var lastSecond by remember { mutableLongStateOf(-1L) }

    LaunchedEffect(timer) {
        if (!timer.running) {
            return@LaunchedEffect
        }

        while (isActive) {
            withFrameNanos { }
            val now = Clock.System.now()
            progress = timer.percentFinished(now)

            val nowSecond = now.epochSeconds
            if (nowSecond != lastSecond) {
                lastSecond = nowSecond
                remainingText = timer.remainingDuration(now).toFormattedDuration()
            }

            if (progress <= 0f) {
                onBack()
                break
            }
        }
    }


    ScreenScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val gap = 30f
            val position = 270f
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        CircularProgressIndicatorDefaults.FullScreenPadding
                    ),
                progress = progress,
                startAngle = position + gap,
                endAngle = position - gap,
                strokeWidth = CircularProgressIndicatorDefaults.largeStrokeWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(CircularProgressIndicatorDefaults.FullScreenPadding)
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                MaterialUButton(
                    onClick = {
                        detailsViewModel.cancelTimer()
                        onBack()
                    },
                    icon = Icons.Rounded.Close,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                )

                Text(
                    remainingText,
                    style = MaterialTheme.typography.numeralSmall.copy(
                        fontSize = 32.sp
                    )
                )


                MaterialUToggleButton(
                    onCheckedChange = { detailsViewModel.toggleTimer() },
                    checked = timer.running,
                    checkedIcon = Icons.Rounded.Pause,
                    uncheckedIcon = Icons.Rounded.PlayArrow
                )

            }
        }
    }
}