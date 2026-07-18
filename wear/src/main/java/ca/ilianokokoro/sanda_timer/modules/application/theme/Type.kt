package ca.ilianokokoro.sanda_timer.modules.application.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ca.ilianokokoro.sanda_timer.R
import androidx.wear.compose.material3.Typography as MaterialTypography

val nunitoFontFamily = FontFamily(
    Font(R.font.nunito_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.nunito_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.nunito, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.nunito_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.nunito_black, FontWeight.Black, FontStyle.Normal),
)
private val defaultTypography = MaterialTypography()

val AppTypography = MaterialTypography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),

    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),

    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),

    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),

    numeralLarge = defaultTypography.numeralLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    numeralMedium = defaultTypography.numeralMedium.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    numeralSmall = defaultTypography.numeralSmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),

    numeralExtraLarge = defaultTypography.numeralExtraLarge.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    ),
    numeralExtraSmall = defaultTypography.numeralExtraSmall.copy(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Bold
    )
)