package com.example.campaign_master.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    )
)
