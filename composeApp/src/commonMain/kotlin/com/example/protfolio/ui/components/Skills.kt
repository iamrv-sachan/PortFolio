package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme

import com.example.protfolio.ui.components.WindowSize

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlow(data: PortfolioResponse, windowSize: WindowSize) {
    val fontSize = if (windowSize == WindowSize.Compact) 24.sp else 36.sp
    
    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.large), verticalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
        data.expertise.technical.forEach { tech ->
            Text(
                text = "${tech.skill} ${tech.progress}%", 
                style = MaterialTheme.typography.bodyMedium, 
                color = PortfolioTheme.colors.text, 
                fontSize = fontSize, 
                fontWeight = FontWeight.Light
            )
        }
    }
}
