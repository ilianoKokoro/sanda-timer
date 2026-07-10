package ca.ilianokokoro.sanda_timer.modules.application.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Typography
import ca.ilianokokoro.sanda_timer.R

val RobotoFlex = FontFamily(
    Font(R.font.roboto_flex)
)

val typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = RobotoFlex),
        displayMedium = displayMedium.copy(fontFamily = RobotoFlex),
        displaySmall = displaySmall.copy(fontFamily = RobotoFlex),

        titleLarge = titleLarge.copy(fontFamily = RobotoFlex),
        titleMedium = titleMedium.copy(fontFamily = RobotoFlex),
        titleSmall = titleSmall.copy(fontFamily = RobotoFlex),

        bodyLarge = bodyLarge.copy(fontFamily = RobotoFlex),
        bodyMedium = bodyMedium.copy(fontFamily = RobotoFlex),
        bodySmall = bodySmall.copy(fontFamily = RobotoFlex),

        labelLarge = labelLarge.copy(fontFamily = RobotoFlex),
        labelMedium = labelMedium.copy(fontFamily = RobotoFlex),
        labelSmall = labelSmall.copy(fontFamily = RobotoFlex)
    )
}

@Composable
fun SandaTimerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        typography = typography
    )
}