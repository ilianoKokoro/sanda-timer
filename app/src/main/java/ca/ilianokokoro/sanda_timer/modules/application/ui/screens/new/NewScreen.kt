package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.EdgeButtonSize
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ScreenScaffold
import ca.ilianokokoro.sanda_timer.modules.application.ui.components.TimerPicker


@Composable
fun NewScreen(
    onBack: () -> Unit,
    newViewModel: NewViewModel = viewModel()
) {
    val uiState = newViewModel.uiState.collectAsStateWithLifecycle().value

    ScreenScaffold {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                TimerPicker()
            }
            EdgeButton(onClick = {
                newViewModel.createTimer()
                onBack()
            }, buttonSize = EdgeButtonSize.Small) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = Icons.Rounded.Check.name,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
            }
        }
    }
}


