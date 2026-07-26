package ca.ilianokokoro.sanda_timer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object TimersScreenKey : NavKey

@Serializable
data object CalculatorKey : NavKey


data class ScreenUiConfig(
    val selectedTab: NavKey? = null
)

@Composable
fun rememberScreenUiConfig(current: NavKey): ScreenUiConfig {
    return remember(current) {
        when (current) {
            CalculatorKey -> ScreenUiConfig(
                selectedTab = CalculatorKey
            )

            TimersScreenKey -> ScreenUiConfig(
                selectedTab = TimersScreenKey
            )

            else -> ScreenUiConfig()
        }
    }
}