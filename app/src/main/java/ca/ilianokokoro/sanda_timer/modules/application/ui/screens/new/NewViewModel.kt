package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import ca.ilianokokoro.sanda_timer.core.data.database.AppDatabase
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration

class NewViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(NewState())
    val uiState = _uiState.asStateFlow()

    val timerDataSource = AppDatabase.getInstance(application).timerDataSource()

    fun updateDuration(newDuration: Duration) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(duration = newDuration)
            }
        }
    }

    fun createTimer() {
        LogHelper.printd("createTimer")
        viewModelScope.launch {
            val duration = uiState.value.duration
            if (duration == Duration.ZERO) {
                LogHelper.printd("Duration.ZERO")
                return@launch
            }

            val endTime = Clock.System.now() + duration
            val newTimer = Timer(endTime = endTime)

            val context = application.applicationContext

            val alarmManager = context.getSystemService(AlarmManager::class.java)

            val id = timerDataSource.insert(newTimer)
            
            val intent = Intent(context, TimerExpiredReceiver::class.java).apply {
                putExtra("timerId", id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                alarmManager.canScheduleExactAlarms()
            ) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    endTime.toEpochMilliseconds(),
                    pendingIntent
                )
            }

        }
    }
}

class TimerExpiredReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LogHelper.printd("onReceive")

        LogHelper.printd(intent.toString())
    }
}