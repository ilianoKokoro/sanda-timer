package ca.ilianokokoro.sanda_timer.modules.application.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun SandaTimerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        typography = AppTypography
    )
}