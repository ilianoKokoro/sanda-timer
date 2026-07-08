package ca.ilianokokoro.sanda_timer.modules.application.screens.new

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text


@Composable
fun NewScreen(
    newViewModel: NewViewModel = viewModel()
) {
    val uiState = newViewModel.uiState.collectAsStateWithLifecycle().value

    AppScaffold {
        ScreenScaffold {
            Text(uiState.seconds.toString())
        }
    }
}


