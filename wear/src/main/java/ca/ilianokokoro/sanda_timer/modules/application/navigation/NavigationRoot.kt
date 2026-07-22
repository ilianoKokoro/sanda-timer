package ca.ilianokokoro.sanda_timer.modules.application.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.helpers.AppIntent
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper.printe
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.details.DetailsScreen
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.main.MainScreen
import ca.ilianokokoro.sanda_timer.modules.application.ui.screens.new.NewScreen
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun NavigationRoot(appIntentFlow: SharedFlow<AppIntent>) {
    val backStack = rememberNavBackStack(Screen.Main)
    val strategy = rememberSwipeDismissableSceneStrategy<NavKey>()
    val app = LocalContext.current.applicationContext as Application
    val timerRepository = TimerRepository(LocalContext.current)

    LaunchedEffect(Unit) {
        appIntentFlow.collect { appIntent ->
            when (appIntent) {
                is AppIntent.OpenTimer -> {
                    timerRepository.getTimerById(appIntent.timerId)?.let {
                        backStack.add(Screen.Details(it))
                    }
                }

                AppIntent.OpenNewScreen -> {
                    backStack.add(Screen.New)
                }

                else -> Unit
            }
        }
    }

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
                    }, onOpenTimer = {
                        backStack.add(Screen.Details(it))
                    })
                }
                entry<Screen.New> {
                    NewScreen(onBack = backStack::safePop)
                }
                entry<Screen.Details> { details ->
                    DetailsScreen(
                        timer = details.timer,
                        application = app,
                        onBack = backStack::safePop,
                    )
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