package ca.ilianokokoro.sanda_timer.modules.application.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()

    val timerDataSource = AppDatabase.getInstance(application).timerDataSource()


    fun createTimer() {
        viewModelScope.launch {
            val newTimer = Timer(endTime = Clock.System.now() + 2.hours)
            timerDataSource.insert(newTimer) // TEMP
        }
    }
}