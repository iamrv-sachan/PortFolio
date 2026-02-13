package com.example.protfolio.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PortfolioConfig(
    val horizontalPadding: Dp,
    val sectionSpacing: Dp,
    val heroTitleSize: TextUnit,
    val sectionTitleSize: TextUnit,
    val bodySize: TextUnit,
    val navFontSize: TextUnit,
    val projectTitleSize: TextUnit,
    val isMobile: Boolean,
    val isTablet: Boolean,
    val mainFont: FontFamily,    // Added Font Support
    val displayFont: FontFamily
)

@Composable
fun rememberPortfolioConfig(screenWidth: Dp): PortfolioConfig {
    val displayFont = FontFamily.SansSerif
    val bodyFont = FontFamily.SansSerif

    return when {
        // Desktop / Large Web
        screenWidth > 1200.dp -> PortfolioConfig(
            horizontalPadding = screenWidth * 0.15f,
            sectionSpacing = 140.dp,
            heroTitleSize = 82.sp,
            sectionTitleSize = 16.sp,
            bodySize = 22.sp,
            navFontSize = 16.sp,
            projectTitleSize = 48.sp,
            isMobile = false,
            isTablet = false,
            mainFont = bodyFont,
            displayFont = displayFont
        )
        // Laptop / Tablet
        screenWidth > 768.dp -> PortfolioConfig(
            horizontalPadding = 64.dp,
            sectionSpacing = 100.dp,
            heroTitleSize = 56.sp,
            sectionTitleSize = 14.sp,
            bodySize = 18.sp,
            navFontSize = 14.sp,
            projectTitleSize = 32.sp,
            isMobile = false,
            isTablet = true,
            mainFont = bodyFont,
            displayFont = displayFont
        )
        // Mobile
        else -> PortfolioConfig(
            horizontalPadding = 24.dp,
            sectionSpacing = 70.dp,
            heroTitleSize = 40.sp,
            sectionTitleSize = 12.sp,
            bodySize = 16.sp,
            navFontSize = 14.sp,
            projectTitleSize = 28.sp,
            isMobile = true,
            isTablet = false,
            mainFont = bodyFont,
            displayFont = displayFont
        )
    }
}