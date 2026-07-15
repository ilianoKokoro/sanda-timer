package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MainState())
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

        viewModelScope.launch {
            while (isActive) {
                _uiState.update {
                    it.copy(now = Clock.System.now())
                }
                delay(1.seconds)
            }
        }
    }
}
