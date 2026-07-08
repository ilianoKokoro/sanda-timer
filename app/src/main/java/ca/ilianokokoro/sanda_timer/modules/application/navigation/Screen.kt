package ca.ilianokokoro.sanda_timer.modules.application.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Main : Screen

    data object New : Screen

}