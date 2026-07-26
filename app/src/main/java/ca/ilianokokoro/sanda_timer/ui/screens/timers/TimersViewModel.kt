package ca.ilianokokoro.sanda_timer.ui.screens.timers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimersViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TimersState())
    val uiState = _uiState.asStateFlow()


}