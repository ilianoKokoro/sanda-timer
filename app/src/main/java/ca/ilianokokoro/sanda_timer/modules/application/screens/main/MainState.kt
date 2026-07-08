package ca.ilianokokoro.sanda_timer.modules.application.screens.main

import ca.ilianokokoro.sanda_timer.models.Timer

data class MainState(
    val timers: List<Timer> = listOf()
)