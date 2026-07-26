package ca.ilianokokoro.sanda_timer.ui.screens.timers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.ilianokokoro.sanda_timer.ui.components.FadingStatusBarWrapper

@Composable
fun TimersScreen(timersViewModel: TimersViewModel = viewModel()) {
    //val uiStateValue = timersViewModel.uiState.collectAsStateWithLifecycle().value

    FadingStatusBarWrapper { statusBarHeight ->
        Column(modifier = Modifier.padding(top = statusBarHeight)) {
            Text("No timer active")
        }
    }

}

