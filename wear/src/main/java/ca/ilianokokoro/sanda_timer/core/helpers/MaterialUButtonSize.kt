package ca.ilianokokoro.sanda_timer.core.helpers

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class MaterialUButtonSize(
    val width: Dp,
    val height: Dp,
    val iconSize: Dp,
) {
    Small(52.dp, 34.dp, 20.dp),
    Medium(68.dp, 46.dp, 24.dp),
}