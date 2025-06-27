// Theme.kt

package com.example.campaign_master.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//Dark theme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC2A878),
    onPrimary = Color(0xFF1B1B1B),
    secondary = Color(0xFF8F5D3F),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF1F1B16),
    onSurface = Color(0xFFE1DED9),
    error = Color(0xFFCF6679)
)

//light theme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6E4E2E),
    onPrimary = Color.White,
    secondary = Color(0xFFA67856),
    onSecondary = Color(0xFF1B1B1B),
    background = Color(0xFFFAF5E9),
    surface = Color.White,
    onSurface = Color(0xFF1B1B1B),
    error = Color(0xFFB00020)
)

@Composable
fun CampaignMasterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
