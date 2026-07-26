package ca.ilianokokoro.sanda_timer.ui.screens.timers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.repositories.TimerRepository
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

}