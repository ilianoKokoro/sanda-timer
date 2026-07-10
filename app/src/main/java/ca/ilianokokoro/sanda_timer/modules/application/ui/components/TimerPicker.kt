package ca.ilianokokoro.sanda_timer.modules.application.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Picker
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPickerState

enum class TimerField {
    HOURS, MINUTES, SECONDS
}

@Composable
fun TimerPicker(
    modifier: Modifier = Modifier,
    maxHours: Int = 99,
    onTimeChanged: (hours: Int, minutes: Int, seconds: Int) -> Unit = { _, _, _ -> }
) {
    val hourState = rememberPickerState(
        initialNumberOfOptions = maxHours + 1,
        initiallySelectedIndex = 0
    )

    val minuteState = rememberPickerState(
        initialNumberOfOptions = 60,
        initiallySelectedIndex = 0
    )

    val secondState = rememberPickerState(
        initialNumberOfOptions = 60,
        initiallySelectedIndex = 0
    )

    var focusedField by remember { mutableStateOf(TimerField.MINUTES) }

    LaunchedEffect(
        hourState.selectedOptionIndex,
        minuteState.selectedOptionIndex,
        secondState.selectedOptionIndex
    ) {
        onTimeChanged(
            hourState.selectedOptionIndex,
            minuteState.selectedOptionIndex,
            secondState.selectedOptionIndex
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Picker(
            state = hourState,
            contentDescription = { "Hours" },
            readOnly = focusedField != TimerField.HOURS,
            onSelected = { focusedField = TimerField.HOURS },
        ) { option ->
            PickerText("%02d".format(option))
        }

        Picker(
            state = minuteState,
            contentDescription = { "Minutes" },
            readOnly = focusedField != TimerField.MINUTES,
            onSelected = { focusedField = TimerField.MINUTES },

            ) { option ->
            PickerText("%02d".format(option))
        }

        Picker(
            state = secondState,
            contentDescription = { "Seconds" },
            readOnly = focusedField != TimerField.SECONDS,
            onSelected = { focusedField = TimerField.SECONDS },
        ) { option ->
            PickerText("%02d".format(option))
        }
    }
}

@Composable
private fun PickerText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
}