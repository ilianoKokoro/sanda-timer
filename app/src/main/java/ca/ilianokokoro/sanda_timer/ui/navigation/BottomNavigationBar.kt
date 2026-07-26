package ca.ilianokokoro.sanda_timer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import ca.ilianokokoro.sanda_timer.core.R

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentTab: NavKey?,
    onTabSelected: (NavKey) -> Unit
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentTab is CalculatorKey,
            onClick = {
                onTabSelected(CalculatorKey)
            },
            icon = {
                Icon(
                    Icons.Default.Calculate,
                    contentDescription = Icons.Default.Calculate.name
                )
            },
            label = { Text(stringResource(R.string.calculator)) }
        )
        NavigationBarItem(
            selected = currentTab is TimersScreenKey,
            onClick = {
                onTabSelected(TimersScreenKey)
            },
            icon = {
                Icon(
                    Icons.Default.Timer,
                    contentDescription = Icons.Default.Timer.name
                )
            },
            label = { Text(stringResource(R.string.timers)) }
        )
    }
}