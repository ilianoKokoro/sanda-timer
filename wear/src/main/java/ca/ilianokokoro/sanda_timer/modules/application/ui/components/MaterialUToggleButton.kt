package ca.ilianokokoro.sanda_timer.modules.application.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconToggleButton
import androidx.wear.compose.material3.IconToggleButtonColors
import androidx.wear.compose.material3.IconToggleButtonDefaults
import ca.ilianokokoro.sanda_timer.core.helpers.MaterialUButtonSize

@Composable
fun MaterialUToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedIcon: ImageVector,
    uncheckedIcon: ImageVector,
    modifier: Modifier = Modifier,
    size: MaterialUButtonSize = MaterialUButtonSize.Medium,
    colors: IconToggleButtonColors = IconToggleButtonDefaults.colors(),
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier
            .width(size.width)
            .height(size.height),
        shapes = IconToggleButtonDefaults.animatedShapes(
            shape = RoundedCornerShape(50), // TODO change the shape for the checked version
        ),
        colors = colors,
    ) {
        val icon = if (checked) {
            checkedIcon
        } else {
            uncheckedIcon
        }

        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            modifier = Modifier.size(size.iconSize)
        )
    }
}