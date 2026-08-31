package ca.ilianokokoro.sanda_timer.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimeInputDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.TimePickerDisplayMode
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.VibrantTimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import ca.ilianokokoro.sanda_timer.core.R


@Composable
fun TimePickerDialog(
    timerPickerState: TimePickerState,
    onDismissRequest: () -> Unit,
) {
    var displayMode by remember { mutableStateOf(TimePickerDisplayMode.Picker) }
    VibrantTimePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.confirmButton))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancelButton))
            }
        },
        modeToggleButton = {
            TimePickerDialogDefaults.DisplayModeToggle(
                onDisplayModeChange = {
                    displayMode =
                        if (displayMode == TimePickerDisplayMode.Picker) {
                            TimePickerDisplayMode.Input
                        } else {
                            TimePickerDisplayMode.Picker
                        }
                },
                displayMode = displayMode,
            )
        },
    ) {
        if (displayMode == TimePickerDisplayMode.Picker) {
            TimePicker(state = timerPickerState, shapes = TimePickerDefaults.shapes())
        } else {
            TimeInput(state = timerPickerState, shapes = TimeInputDefaults.shapes())
        }
    }
}
