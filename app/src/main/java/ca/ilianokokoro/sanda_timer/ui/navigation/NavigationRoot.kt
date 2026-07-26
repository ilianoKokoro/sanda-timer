package ca.ilianokokoro.sanda_timer.ui.navigation

import android.app.Application
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.core.helpers.AppIntent
import ca.ilianokokoro.sanda_timer.core.helpers.LogHelper
import ca.ilianokokoro.sanda_timer.ui.screens.calculator.CalculatorScreen
import ca.ilianokokoro.sanda_timer.ui.screens.timers.TimersScreen
import kotlinx.coroutines.flow.SharedFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(appIntentFlow: SharedFlow<AppIntent>) {
    val backStack = rememberNavBackStack(CalculatorKey)
    val app = LocalContext.current.applicationContext as Application
    val currentScreen = backStack.last()
    val screenConfig = rememberScreenUiConfig(currentScreen)


    LaunchedEffect(Unit) {
        appIntentFlow.collect { appIntent ->
            when (appIntent) {

                AppIntent.OpenTimerPage -> {
                    backStack.add(TimersScreenKey)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            BottomNavigationBar(
                currentTab = screenConfig.selectedTab,
                onTabSelected = { key ->
                    if (backStack.last() != key) {
                        backStack.add(key)
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            NavDisplay(
                modifier = Modifier
                    .fillMaxSize(),
                backStack = backStack,
                onBack = backStack::safePop,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                transitionSpec = {
                    (scaleIn(
                        animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                        initialScale = 0.85f
                    ) +
                            fadeIn(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION))) togetherWith
                            (scaleOut(
                                animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                                targetScale = 1.1f
                            ) +
                                    fadeOut(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION)))
                },
                popTransitionSpec = {
                    (scaleIn(
                        animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                        initialScale = 1.1f
                    ) +
                            fadeIn(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION))) togetherWith
                            (scaleOut(
                                animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                                targetScale = 0.85f
                            ) +
                                    fadeOut(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION)))
                },
                predictivePopTransitionSpec = {
                    (scaleIn(
                        animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                        initialScale = 1.1f
                    ) +
                            fadeIn(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION))) togetherWith
                            (scaleOut(
                                animationSpec = tween(Constants.Animation.NAVIGATION_DURATION),
                                targetScale = 0.85f
                            ) +
                                    fadeOut(animationSpec = tween(Constants.Animation.NAVIGATION_DURATION)))
                },
                entryProvider = { key ->
                    when (key) {

                        is CalculatorKey -> NavEntry(key) {
                            CalculatorScreen(onOpenTimerScreen = {
                                backStack.add(TimersScreenKey)
                            })
                        }

                        is TimersScreenKey -> NavEntry(key) {
                            TimersScreen()
                        }


                        else -> throw RuntimeException(
                            app.getString(
                                R.string.invalid_navkey,
                                key
                            )
                        )
                    }
                }
            )


        }

    }

}


fun NavBackStack<NavKey>.safePop() {
    if (this.size > 1) {
        this.removeLastOrNull()
    } else {
        LogHelper.printe("Backstack Pop was called unsafely")
    }
}