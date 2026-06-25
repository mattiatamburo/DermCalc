package com.example.dermcalc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import android.graphics.Typeface

val ComicSansFamily = FontFamily(Typeface.create("sans-serif-casual", Typeface.NORMAL))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ComicSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ComicSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ComicSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(fontFamily = ComicSansFamily),
    displayMedium = TextStyle(fontFamily = ComicSansFamily),
    displaySmall = TextStyle(fontFamily = ComicSansFamily),
    headlineLarge = TextStyle(fontFamily = ComicSansFamily),
    headlineMedium = TextStyle(fontFamily = ComicSansFamily),
    headlineSmall = TextStyle(fontFamily = ComicSansFamily),
    titleMedium = TextStyle(fontFamily = ComicSansFamily),
    titleSmall = TextStyle(fontFamily = ComicSansFamily),
    bodyMedium = TextStyle(fontFamily = ComicSansFamily),
    bodySmall = TextStyle(fontFamily = ComicSansFamily),
    labelLarge = TextStyle(fontFamily = ComicSansFamily),
    labelMedium = TextStyle(fontFamily = ComicSansFamily)
)