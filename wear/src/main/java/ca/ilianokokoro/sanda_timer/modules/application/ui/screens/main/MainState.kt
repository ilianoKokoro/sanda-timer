package ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main

import ca.ilianokokoro.sanda_timer.models.Timer
import kotlin.time.Clock
import kotlin.time.Instant

data class MainState(
    val timers: List<Timer> = listOf(),
    val now: Instant = Clock.System.now()
)