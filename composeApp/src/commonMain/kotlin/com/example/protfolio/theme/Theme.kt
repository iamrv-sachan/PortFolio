package com.example.protfolio.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import protfolio.composeapp.generated.resources.Geist_Black
import protfolio.composeapp.generated.resources.Geist_Bold
import protfolio.composeapp.generated.resources.Geist_Light
import protfolio.composeapp.generated.resources.Geist_Medium
import protfolio.composeapp.generated.resources.Geist_Regular
import protfolio.composeapp.generated.resources.Geist_SemiBold
import protfolio.composeapp.generated.resources.Res

// Define Geist Font Family
@Composable
fun GeistFontFamily() = FontFamily(
    Font(Res.font.Geist_Light, FontWeight.Light),
    Font(Res.font.Geist_Regular, FontWeight.Normal),
    Font(Res.font.Geist_Medium, FontWeight.Medium),
    Font(Res.font.Geist_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Geist_Bold, FontWeight.Bold),
    Font(Res.font.Geist_Black, FontWeight.Black)
)


// Define our custom colors
@Immutable
data class PortfolioColors(
    val background: Color,
    val surface: Color,
    val text: Color,
    val secondaryText: Color,
    val accent: Color,
    val border: Color,
    val isDark: Boolean
)

val DarkPalette = PortfolioColors(
    background = Color(0xFF161616),
    surface = Color(0xFF1E1E1E),
    text = Color(0xFFEDEDED),
    secondaryText = Color(0xFFA1A1A1),
    accent = Color(0xFF3B82F6), // A slightly softer blue/accent
    border = Color(0xFF333333),
    isDark = true
)

val LightPalette = PortfolioColors(
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    text = Color(0xFF0A0A0A),
    secondaryText = Color(0xFF666666),
    accent = Color(0xFF1976D2),
    border = Color(0xFFE0E0E0),
    isDark = false
)

// CompositionLocal for our custom colors
val LocalPortfolioColors = staticCompositionLocalOf { DarkPalette }

// Controller for theme switching
class ThemeController(isDark: Boolean) {
    var isDark by mutableStateOf(isDark)
        private set

    fun toggle() {
        isDark = !isDark
    }
}

val LocalThemeController = compositionLocalOf<ThemeController> { error("No ThemeController provided") }

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // Enforce Dark Mode
    val themeController = remember { ThemeController(isDark = true) }
    
    val colors = DarkPalette // Always use DarkPalette

    // Map to MaterialTheme colors for interop with Material components
    val materialColors = if (themeController.isDark) {
        darkColorScheme(
            primary = colors.accent,
            background = colors.background,
            surface = colors.surface,
            onBackground = colors.text,
            onSurface = colors.text
        )
    } else {
        lightColorScheme(
            primary = colors.accent,
            background = colors.background,
            surface = colors.surface,
            onBackground = colors.text,
            onSurface = colors.text
        )
    }

    val geistFontFamily = GeistFontFamily()

    val typography = Typography(
        displayLarge = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 72.sp,
            lineHeight = 80.sp,
            letterSpacing = (-2).sp
        ),
        displayMedium = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp,
            lineHeight = 64.sp,
            letterSpacing = (-1).sp
        ),
        displaySmall = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            lineHeight = 56.sp,
            letterSpacing = (-0.5).sp
        ),
        headlineLarge = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.4.sp
        ),
        labelLarge = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )

    val spacing = Spacing()

    CompositionLocalProvider(
        LocalPortfolioColors provides colors,
        LocalThemeController provides themeController,
        LocalSpacing provides spacing
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = typography,
            content = content
        )
    }
}

// Convenience accessor
object PortfolioTheme {
    val colors: PortfolioColors
        @Composable
        get() = LocalPortfolioColors.current
    
    val controller: ThemeController
        @Composable
        get() = LocalThemeController.current

    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current
}
