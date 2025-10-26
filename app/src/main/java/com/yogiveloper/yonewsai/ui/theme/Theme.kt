package com.yogiveloper.yonewsai.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Color palette for Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary80,                 // Light blue for contrast on dark background
    onPrimary = Color(0xFF002D62),           // Very dark blue for text on primary
    primaryContainer = Color(0xFF004495),    // Dark blue for containers
    onPrimaryContainer = Color(0xFFD7E2FF),  // Very light blue for text on container

    secondary = BlueSecondary80,             // Light grayish blue
    onSecondary = Color(0xFF263141),         // Dark gray for text on secondary
    secondaryContainer = Color(0xFF3C4758),  // Medium gray for containers
    onSecondaryContainer = Color(0xFFD7E3F8),// Light gray for text on container
    tertiary = BlueTertiary80,               // Light sky blue
    onTertiary = Color(0xFF003352),          // Dark blue for text
    tertiaryContainer = Color(0xFF004A75),   // Medium blue for containers
    onTertiaryContainer = Color(0xFFCDE5FF), // Pale blue for text

    background = Color(0xFF1B1B1F),          // Almost black background
    onBackground = Color(0xFFE3E2E6),        // Light gray text on dark background

    surface = Color(0xFF1B1B1F),             // Same as background
    onSurface = Color(0xFFE3E2E6),           // Text on surface
    surfaceVariant = Color(0xFF44474E),      // Surface variant (e.g. text field outline)
    onSurfaceVariant = Color(0xFFC4C6D0),    // Text on surface variant
)

// Color palette for Light Theme
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary40,                 // Main iOS-style blue
    onPrimary = Color.White,                 // White text on primary
    primaryContainer = Color(0xFFD7E2FF),    // Very pale blue container
    onPrimaryContainer = Color(0xFF001A40),  // Very dark blue text on container

    secondary = BlueSecondary40,             // Dark grayish blue
    onSecondary = Color.White,               // White text on secondary
    secondaryContainer = Color(0xFFD7E3F8),  // Pale gray container
    onSecondaryContainer = Color(0xFF121D2C),// Dark gray text on container

    tertiary = BlueTertiary40,               // Deeper blue
    onTertiary = Color.White,                // White text on tertiary
    tertiaryContainer = Color(0xFFCDE5FF),   // Pale sky blue container
    onTertiaryContainer = Color(0xFF001E30), // Dark blue text on container

    background = Color(0xFFF8F8FA),          // Slightly off-white background
    onBackground = Color(0xFF1B1B1F),        // Almost black text on light background
    surface = Color(0xFFF8F8FA),             // Component surface (cards, etc.)
    onSurface = Color(0xFF1B1B1F),           // Text on surface
    surfaceVariant = Color(0xFFE1E2EC),      // Surface variant (e.g. outline)
    onSurfaceVariant = Color(0xFF44474E),    // Text on surface variant
)

@Composable
fun YoNewsAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
