package ca.ilianokokoro.sanda_timer.modules.application.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import ca.ilianokokoro.sanda_timer.modules.application.screens.main.MainScreen
import ca.ilianokokoro.sanda_timer.modules.application.screens.new.NewScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Screen.Main)
    val strategy = rememberSwipeDismissableSceneStrategy<NavKey>()

    NavDisplay(
        backStack = backStack,
        sceneStrategies = listOf(strategy),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Main> {
                MainScreen(onCreateTimer = {
                    backStack.add(Screen.New)
                })
            }
            entry<Screen.New> {
                NewScreen()
            }
        }
    )
}