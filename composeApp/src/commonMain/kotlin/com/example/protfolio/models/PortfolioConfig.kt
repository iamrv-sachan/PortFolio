package com.example.protfolio.models

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

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