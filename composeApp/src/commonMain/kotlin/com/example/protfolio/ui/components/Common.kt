package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.protfolio.theme.PortfolioTheme

enum class WindowSize {
    Compact, Medium, Expanded
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, color = PortfolioTheme.colors.secondaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 4.sp, modifier = Modifier.padding(bottom = PortfolioTheme.spacing.section))
}
