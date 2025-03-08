package com.example.hanyarunrun.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Tema Terang
private val LightColorPalette = HanyarunrunColors(
    brand = Ocean8,
    brandSecondary = Tosca9,
    uiBackground = Neutral0,
    uiBorder = Neutral4,
    uiFloated = Ocean1,
    textPrimary = Ocean11,
    textSecondary = Neutral6,
    textHelp = Tosca10,
    textInteractive = Ocean9,
    textLink = Tosca8,
    iconSecondary = Neutral5,
    iconInteractive = Ocean7,
    iconInteractiveInactive = Neutral4,
    error = Ocean11,
    gradient6_1 = listOf(Tosca9, Tosca7, Tosca5, Ocean7, Ocean5, Ocean3),
    gradient6_2 = listOf(Ocean11, Ocean9, Ocean7, Tosca9, Tosca7, Tosca5),
    gradient3_1 = listOf(Tosca9, Tosca7, Ocean7),
    gradient3_2 = listOf(Ocean9, Ocean7, Tosca9),
    gradient2_1 = listOf(Tosca8, Ocean8),
    gradient2_2 = listOf(Ocean7, Tosca7),
    gradient2_3 = listOf(Tosca9, Ocean9),
    tornado1 = listOf(Ocean8, Tosca8),
    isDark = false
)

// Tema Gelap
private val DarkColorPalette = HanyarunrunColors(
    brand = Ocean7,
    brandSecondary = Tosca7,
    uiBackground = Neutral8,
    uiBorder = Neutral6,
    uiFloated = Ocean10,
    textPrimary = Neutral1,
    textSecondary = Neutral2,
    textHelp = Tosca6,
    textInteractive = Ocean6,
    textLink = Tosca5,
    iconPrimary = Ocean6,
    iconSecondary = Neutral5,
    iconInteractive = Tosca7,
    iconInteractiveInactive = Neutral6,
    error = Ocean9,
    gradient6_1 = listOf(Ocean10, Ocean8, Ocean6, Tosca7, Tosca5, Tosca3),
    gradient6_2 = listOf(Neutral1, Ocean7, Ocean5, Tosca7, Tosca5, Neutral2),
    gradient3_1 = listOf(Tosca7, Ocean7, Neutral2),
    gradient3_2 = listOf(Ocean9, Ocean7, Tosca6),
    gradient2_1 = listOf(Tosca7, Ocean7),
    gradient2_2 = listOf(Ocean6, Tosca6),
    gradient2_3 = listOf(Tosca8, Neutral2),
    tornado1 = listOf(Ocean7, Tosca7),
    isDark = true
)

// ColorScheme untuk tema terang
private val LightColorScheme = lightColorScheme(
    primary = Ocean8,
    onPrimary = Neutral0,
    secondary = Tosca9,
    onSecondary = Neutral0,
    background = Neutral0,
    onBackground = Ocean11,
    surface = Ocean1,
    onSurface = Ocean11,
    error = Ocean11,
    onError = Neutral0
)

// ColorScheme untuk tema gelap
private val DarkColorScheme = darkColorScheme(
    primary = Ocean7,
    onPrimary = Neutral1,
    secondary = Tosca7,
    onSecondary = Neutral1,
    background = Neutral8,
    onBackground = Neutral1,
    surface = Ocean10,
    onSurface = Neutral1,
    error = Ocean9,
    onError = Neutral1
)

@Composable
fun HanyarunrunTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    ProvideHanyarunrunColors(colors) {
        MaterialTheme(
            colorScheme = colorScheme, // Menggunakan ColorScheme yang sesuai, bukan debugColors
            typography = HanyarunrunTypography,
            shapes = Shapes,
            content = content
        )
    }
}

object HanyarunrunTheme {
    val colors: HanyarunrunColors
        @Composable
        get() = LocalHanyarunrunColors.current
}

@Immutable
data class HanyarunrunColors(
    val gradient6_1: List<Color>,
    val gradient6_2: List<Color>,
    val gradient3_1: List<Color>,
    val gradient3_2: List<Color>,
    val gradient2_1: List<Color>,
    val gradient2_2: List<Color>,
    val gradient2_3: List<Color>,
    val brand: Color,
    val brandSecondary: Color,
    val uiBackground: Color,
    val uiBorder: Color,
    val uiFloated: Color,
    val interactivePrimary: List<Color> = gradient2_1,
    val interactiveSecondary: List<Color> = gradient2_2,
    val interactiveMask: List<Color> = gradient6_1,
    val textPrimary: Color = brand,
    val textSecondary: Color,
    val textHelp: Color,
    val textInteractive: Color,
    val textLink: Color,
    val tornado1: List<Color>,
    val iconPrimary: Color = brand,
    val iconSecondary: Color,
    val iconInteractive: Color,
    val iconInteractiveInactive: Color,
    val error: Color,
    val notificationBadge: Color = error,
    val isDark: Boolean
)

@Composable
fun ProvideHanyarunrunColors(
    colors: HanyarunrunColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalHanyarunrunColors provides colors, content = content)
}

private val LocalHanyarunrunColors = staticCompositionLocalOf<HanyarunrunColors> {
    error("No HanyarunrunColorPalette provided")
}