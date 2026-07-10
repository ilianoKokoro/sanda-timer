package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration

class NewViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(NewState())
    val uiState = _uiState.asStateFlow()

    val timerDataSource = AppDatabase.getInstance(application).timerDataSource()

    fun createTimer() {
        viewModelScope.launch {
            val duration = uiState.value.duration
            if (duration == Duration.ZERO) {
                return@launch
            }

            val newTimer = Timer(endTime = Clock.System.now() + duration)
            timerDataSource.insert(newTimer) // TEMP
        }
    }
}