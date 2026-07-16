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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.EdgeButtonSize
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import ca.ilianokokoro.sanda_timer.modules.application.ui.components.TimerPicker


@Composable
fun NewScreen(
    onBack: () -> Unit,
    newViewModel: NewViewModel = viewModel()
) {
    ScreenScaffold {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                TimerPicker(onTimeChanged = {
                    newViewModel.updateDuration(it)
                })
            }
            EdgeButton(
                onClick = {
                    newViewModel.createTimer()
                    onBack()
                },
                buttonSize = EdgeButtonSize.Small,
                colors =
                    ButtonDefaults.buttonColors(
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


