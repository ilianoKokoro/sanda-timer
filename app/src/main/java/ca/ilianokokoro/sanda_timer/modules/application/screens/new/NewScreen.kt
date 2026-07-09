package ca.ilianokokoro.sanda_timer.modules.application.screens.new

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text


@Composable
fun NewScreen(
    onBack: () -> Unit,
    newViewModel: NewViewModel = viewModel()
) {
    val uiState = newViewModel.uiState.collectAsStateWithLifecycle().value

    AppScaffold {
        ScreenScaffold { contentPadding ->
            Column {
                Row(modifier = Modifier.padding(contentPadding)) {
                    Text(uiState.seconds.toString())
                }

                Button(onClick = {
                    newViewModel.createTimer()
                    onBack()
                }) {
                    Icon(Icons.Rounded.Add, contentDescription = Icons.Rounded.Add.name)
                }
            }
        }
    }
}


