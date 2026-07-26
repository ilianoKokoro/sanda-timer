package ca.ilianokokoro.sanda_timer.ui.screens.timers

import ca.ilianokokoro.sanda_timer.models.Timer

data class TimersState(
    val timers: List<Timer> = listOf()
)