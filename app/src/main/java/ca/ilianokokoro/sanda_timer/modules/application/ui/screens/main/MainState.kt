package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main

import ca.ilianokokoro.sanda_timer.models.Timer

data class MainState(
    val timers: List<Timer> = listOf()
)