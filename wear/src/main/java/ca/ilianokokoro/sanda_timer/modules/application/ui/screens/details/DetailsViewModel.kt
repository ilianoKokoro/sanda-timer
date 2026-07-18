package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel(application: Application, timer: Timer) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DetailsState(timer = timer))
    val uiState = _uiState.asStateFlow()

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
