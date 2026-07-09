package ca.ilianokokoro.sanda_timer.modules.application.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.FilledIconButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import ca.ilianokokoro.sanda_timer.modules.application.screens.main.component.TimerPill


@Composable
fun MainScreen(
    onCreateTimer: () -> Unit,
    mainViewModel: MainViewModel = viewModel()
) {
    val uiState = mainViewModel.uiState.collectAsStateWithLifecycle().value
    val transformationSpec = rememberTransformationSpec()
    val listState = rememberTransformingLazyColumnState()
    val timers = uiState.timers

    AppScaffold {
        ScreenScaffold(scrollState = listState) { contentPadding ->
            TransformingLazyColumn(
                state = listState,
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                if (timers.isNotEmpty()) {
                    items(timers, key = { it.id }) { timer ->
                        TimerPill(
                            timer,
                            modifier = Modifier.transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec)
                        )
                    }
                } else {
                    item {
                        Text(
                            "No timers",
                            modifier = Modifier.transformedHeight(this, transformationSpec)
                        )
                    }
                }
                item {
                    FilledIconButton(
                        onClick = onCreateTimer,
                        modifier = Modifier.transformedHeight(this, transformationSpec),
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = Icons.Rounded.Add.name)
                    }
                }
            }
        }
    }
}

