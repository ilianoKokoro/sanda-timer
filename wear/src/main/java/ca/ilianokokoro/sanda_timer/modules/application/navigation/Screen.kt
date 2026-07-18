package ca.ilianokokoro.sanda_timer.modules.application.navigation

import androidx.navigation3.runtime.NavKey
import ca.ilianokokoro.sanda_timer.models.Timer
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Main : Screen

    @Serializable
    data object New : Screen

    @Serializable
    data class Details(val timer: Timer) : Screen
}