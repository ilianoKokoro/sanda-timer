package ca.ilianokokoro.sanda_timer.ui.screens.calculator

import androidx.compose.material3.TimePickerState
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.models.Calculation

data class CalculatorState(
    val calculation: Calculation = Calculation(),
    val resultText: String = "",
    val checkBoxState: Boolean = Constants.DefaultValues.OFFSET,
    val timePickerState: TimePickerState,
    val showTimePicker: Boolean = false
)