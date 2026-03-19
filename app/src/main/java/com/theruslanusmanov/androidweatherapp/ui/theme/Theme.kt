package com.theruslanusmanov.androidweatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    background = Color.Black
    // ..
)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
//)

// Create a custom Typography object
val weatherTypography = Typography(
    displayLarge = TextStyle(
        color = Color.White,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 120.sp,
        lineHeight = 60.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        color = Color.White,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        color = Color.White,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        color = Color.White,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge  = TextStyle(
        color = Color.White,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 64.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // Define other styles as needed
)

@Composable
fun AndroidWeatherAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = weatherTypography,
        shapes = Shapes,
        content = content
    )
}