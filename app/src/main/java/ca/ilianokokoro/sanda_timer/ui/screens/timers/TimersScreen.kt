package ca.ilianokokoro.sanda_timer.ui.screens.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.ui.components.FadingStatusBarWrapper
import ca.ilianokokoro.sanda_timer.ui.screens.timers.components.TimerListItem

@Composable
fun TimersScreen(timersViewModel: TimersViewModel = viewModel()) {
    val uiStateValue = timersViewModel.uiState.collectAsStateWithLifecycle().value

    FadingStatusBarWrapper { statusBarHeight ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = statusBarHeight),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val timers = uiStateValue.timers
            if (timers.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_timers),
                    style = MaterialTheme.typography.titleLarge
                )

            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(
                        items = timers,
                        key = { timer ->
                            timer.id
                        }) {
                        TimerListItem(
                            timer = it,
                            onOpenTimer = {},
                            onCancel = { timersViewModel.cancelTimer(it) })
                    }
                }
            }

        }
    }

}

