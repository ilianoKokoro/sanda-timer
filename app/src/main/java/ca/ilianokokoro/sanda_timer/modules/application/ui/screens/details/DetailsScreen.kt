package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.details

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material3.CircularProgressIndicatorDefaults
import androidx.wear.compose.material3.ScreenScaffold
import ca.ilianokokoro.sanda_timer.models.Timer
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

    var progress by remember { mutableFloatStateOf(timer.percentFinished(Clock.System.now())) }
    var lastSecond by remember { mutableLongStateOf(-1L) }

    LaunchedEffect(timer) {
        while (isActive) { // TODO : check if this needs a rework
            withFrameNanos { }
            val now = Clock.System.now()
            progress = timer.percentFinished(now)

            val nowSecond = now.epochSeconds
            if (nowSecond != lastSecond) {
                lastSecond = nowSecond
            }

            if (progress >= 1f) {
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
                strokeWidth = CircularProgressIndicatorDefaults.smallStrokeWidth
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

            }

        }
    }
}