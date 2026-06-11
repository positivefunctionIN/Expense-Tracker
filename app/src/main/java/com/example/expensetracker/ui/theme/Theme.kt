// ui/theme/Theme.kt
package com.example.expensetracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Light theme color scheme
 */
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = OnSecondary,
    tertiary = Secondary,
    onTertiary = OnSecondary,
    tertiaryContainer = SecondaryVariant,
    onTertiaryContainer = OnSecondary,
    error = Error,
    onError = OnPrimary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color(0xFF767676),
    outline = Color(0xFF999999),
    outlineVariant = Color(0xFFCCCCCC),
    scrim = Color(0xFF000000)
)

/**
 * Dark theme color scheme
 */
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = OnSecondary,
    tertiary = Secondary,
    onTertiary = OnSecondary,
    tertiaryContainer = SecondaryVariant,
    onTertiaryContainer = OnSecondary,
    error = Error,
    onError = OnPrimary,
    background = Color(0xFF121212),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFEDEDED),
    surfaceVariant = Color(0xFF383838),
    onSurfaceVariant = Color(0xFFC8C8C8),
    outline = Color(0xFF898989),
    outlineVariant = Color(0xFF484848),
    scrim = Color(0xFF000000)
)

/**
 * Main theme composable
 * Apply this to your entire app
 */
@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    dynamicColor: Boolean = true, // Use dynamic color on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}