package ca.ilianokokoro.sanda_timer.modules.application.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper.printe
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.MainScreen
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new.NewScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Screen.Main)
    val strategy = rememberSwipeDismissableSceneStrategy<NavKey>()

    AppScaffold {
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
                    NewScreen(onBack = {
                        backStack.safePop()
                    })
                }
            }
        )
    }
}

fun NavBackStack<NavKey>.safePop() {
    if (this.size > 1) {
        this.removeLastOrNull()
    } else {
        printe("Backstack Pop was called unsafely")
    }
}