package ca.ilianokokoro.sanda_timer.modules.application.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.wear.compose.material3.FilledTonalIconButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButtonColors
import androidx.wear.compose.material3.IconButtonDefaults
import ca.ilianokokoro.sanda_timer.core.helpers.MaterialUButtonSize

@Composable
fun MaterialUButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: MaterialUButtonSize = MaterialUButtonSize.Small,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier
            .width(size.width)
            .height(size.height),
        shapes = IconButtonDefaults.animatedShapes(
            shape = RoundedCornerShape(50)
        ),
        colors = colors,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            modifier = Modifier.size(size.iconSize)
        )
    }
}