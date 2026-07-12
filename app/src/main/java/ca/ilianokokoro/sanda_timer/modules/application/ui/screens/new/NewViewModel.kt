package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

class NewViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(NewState())
    val uiState = _uiState.asStateFlow()
    private val timerRepository = TimerRepository(application)


    fun updateDuration(newDuration: Duration) {
        _uiState.update {
            it.copy(duration = newDuration)
        }
    }

    fun createTimer() {
        LogHelper.printd("createTimer, duration : ${uiState.value.duration}")
        viewModelScope.launch {
            timerRepository.createTimer(uiState.value.duration)
        }
    }
}