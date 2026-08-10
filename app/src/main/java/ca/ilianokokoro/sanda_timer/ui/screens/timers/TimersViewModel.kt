package ca.ilianokokoro.sanda_timer.ui.screens.timers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimersViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TimersState())
    val uiState = _uiState.asStateFlow()


    val timerDataSource = AppDatabase.getInstance(application).timerDataSource()
    val timerRepository = TimerRepository(application)

    init {
        viewModelScope.launch {
            TimerRepository(application).deleteExpiredTimers()
        }

        timerDataSource.getAllFlow()
            .onEach { timers ->
                _uiState.update { it.copy(timers = timers) }
            }
            .launchIn(viewModelScope)
    }

    fun cancelTimer(timer: Timer) {
        viewModelScope.launch {
            timerRepository.deleteTimer(timer)
        }
    }

    fun toggleTimer(timer: Timer) {
        viewModelScope.launch {
            val newTimer = timerRepository.toggleTimer(timer)

            _uiState.update { state ->
                state.copy(
                    timers = state.timers.map {
                        if (it.id == newTimer.id) {
                            newTimer
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }
}