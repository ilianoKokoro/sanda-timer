package ca.ilianokokoro.sanda_timer.core

import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.core.content.res.ResourcesCompat

@Composable
fun String.withCenteredColons(style: TextStyle): AnnotatedString {
    val context = LocalContext.current
    val density = LocalDensity.current
    return remember(this, style) {
        val fontSizePx = with(density) { style.fontSize.toPx() }
        val paint = android.graphics.Paint().apply {
            textSize = fontSizePx
            ResourcesCompat.getFont(context, R.font.nunito_bold)?.let { typeface = it }
        }

        val metrics = paint.fontMetrics
        val fontCenter = (metrics.ascent + metrics.descent) / 2f

        val bounds = Rect()
        paint.getTextBounds(":", 0, 1, bounds)
        val colonCenter = (bounds.top + bounds.bottom) / 2f

        val shift = (fontCenter - colonCenter) / fontSizePx

        buildAnnotatedString {
            for (char in this@withCenteredColons) {
                if (char == ':') {
                    withStyle(SpanStyle(baselineShift = BaselineShift(-shift))) {
                        append(char)
                    }
                } else {
                    append(char)
                }
            }
        }
    }
}
