package ca.ilianokokoro.sanda_timer.modules.application.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.PickerGroup
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPickerState
import ca.ilianokokoro.sanda_timer.R
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerPicker(
    modifier: Modifier = Modifier,
    maxHours: Int = 99,
    onTimeChanged: (Duration) -> Unit = { _ -> },
) {
    val showHours = maxHours > 0
    val columnWidth = 36.dp
    val columnHeight = 46.dp

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

    var focusedColumn by remember {
        mutableIntStateOf(
            if (showHours) {
                0
            } else {
                1
            }
        )
    }

    LaunchedEffect(Unit) {
        snapshotFlow {
            Triple(
                hourState.selectedOptionIndex,
                minuteState.selectedOptionIndex,
                secondState.selectedOptionIndex,
            )
        }.collect { (hours, minutes, seconds) ->
            onTimeChanged(
                hours.hours + minutes.minutes + seconds.seconds
            )
        }
    }
    val selectedState = when (focusedColumn) {
        0 -> hourState
        1 -> minuteState
        else -> secondState
    }

    Box(modifier = modifier.fillMaxSize()) {
        PickerGroup(
            modifier = Modifier.align(Alignment.Center),
            selectedPickerState = selectedState,
            autoCenter = false
        ) {
            if (showHours) {
                PickerGroupItem(
                    pickerState = hourState,
                    selected = focusedColumn == 0,
                    onSelected = { focusedColumn = 0 },
                    option = { index, selected -> DigitColumn(index, selected) },
                    contentDescription = { "Hours" },
                    modifier = Modifier.size(width = columnWidth, height = columnHeight)
                )
                Colon()
            }
            PickerGroupItem(
                pickerState = minuteState,
                selected = focusedColumn == 1,
                onSelected = { focusedColumn = 1 },
                option = { index, selected -> DigitColumn(index, selected) },
                contentDescription = { "Minutes" },
                modifier = Modifier.size(width = columnWidth, height = columnHeight)
            )
            Colon()
            PickerGroupItem(
                pickerState = secondState,
                selected = focusedColumn == 2,
                onSelected = { focusedColumn = 2 },
                option = { index, selected -> DigitColumn(index, selected) },
                contentDescription = { "Seconds" },
                modifier = Modifier.size(width = columnWidth, height = columnHeight)
            )
        }
    }
}

@Composable
private fun DigitColumn(
    optionIndex: Int,
    rowSelected: Boolean,
) {
    Text(
        text = String.format(Locale.ROOT, "%02d", optionIndex),
        style = MaterialTheme.typography.numeralSmall,
        color = when {
            rowSelected -> MaterialTheme.colorScheme.onBackground
            else -> MaterialTheme.colorScheme.secondaryDim
        },
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun Colon() {
    Text(
        text = stringResource(R.string.colon),
        style = MaterialTheme.typography.numeralSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 2.dp)
    )
}