package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()
    val timerDataSource = AppDatabase.getInstance(application).timerDataSource()

    init {
        timerDataSource.getAllFlow()
            .onEach { timers ->
                _uiState.update { it.copy(timers = timers) }
            }
            .launchIn(viewModelScope)
    }
}