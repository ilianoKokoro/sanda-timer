package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application, timer: Timer) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DetailsState(timer = timer))
    val uiState = _uiState.asStateFlow()

    private val timerRepository = TimerRepository(application)

    fun toggleTimer() {
        viewModelScope.launch {
            val newTimer = timerRepository.toggleTimer(uiState.value.timer)
            _uiState.update {
                it.copy(timer = newTimer)
            }
        }
    }

    fun cancelTimer() {
        viewModelScope.launch {
            timerRepository.deleteTimer(uiState.value.timer)
        }
    }


    companion object {
        fun Factory(
            application: Application,
            timer: Timer
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    DetailsViewModel(application, timer)
                }
            }
    }
}
