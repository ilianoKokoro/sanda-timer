package ca.ilianokokoro.sanda_timer.modules.application.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import ca.ilianokokoro.sanda_timer.modules.application.screens.main.component.TimerPill


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val uiState = mainViewModel.uiState.collectAsStateWithLifecycle().value
    val transformationSpec = rememberTransformationSpec()

    AppScaffold {
        ScreenScaffold {
            val timers = uiState.timers
            TransformingLazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Button(
                        onClick = mainViewModel::createTimer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec),
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = Icons.Rounded.Add.name)
                    }
                }

                if (timers.isNotEmpty()) {
                    items(uiState.timers) { timer ->
                        TimerPill(timer)
                    }
                } else {
                    item {
                        Text("No timers")
                    }
                }

            }

        }
    }
}