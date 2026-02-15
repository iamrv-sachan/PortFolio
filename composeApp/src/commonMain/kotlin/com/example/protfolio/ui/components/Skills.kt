package com.example.protfolio.ui.components

import androidx.compose.foundation.border
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
import com.example.protfolio.model.TechnicalSkillResponse
import com.example.protfolio.theme.PortfolioTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.protfolio.ui.components.WindowSize

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlow(data: PortfolioResponse, windowSize: WindowSize) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Technical Skills - 2 Columns
        val technicalSkills = data.expertise.technical
        val midPoint = (technicalSkills.size + 1) / 2
        val leftColumn = technicalSkills.take(midPoint)
        val rightColumn = technicalSkills.drop(midPoint)

        if (windowSize == WindowSize.Compact) {
            technicalSkills.forEach { skill ->
                SkillProgressBar(skill)
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.section)) {
                Column(modifier = Modifier.weight(1f)) {
                    leftColumn.forEach { skill ->
                        SkillProgressBar(skill)
                        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    rightColumn.forEach { skill ->
                        SkillProgressBar(skill)
                        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))

        // Exploring Section
        Text(
            text = "CURRENTLY EXPLORING",
            style = MaterialTheme.typography.labelMedium,
            color = PortfolioTheme.colors.accent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.medium)
        ) {
            listOf("Kotlin Multiplatform", "Compose Multiplatform", "Server-Side Kotlin (Ktor)", "System Design").forEach { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PortfolioTheme.colors.text,
                    modifier = Modifier
                        .border(1.dp, PortfolioTheme.colors.secondaryText.copy(alpha = 0.5f), RoundedCornerShape(50))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SkillProgressBar(skill: TechnicalSkillResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            Text(
                text = skill.skill,
                style = MaterialTheme.typography.titleMedium,
                color = PortfolioTheme.colors.text,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${skill.progress}%", // Use percentage
                style = MaterialTheme.typography.labelMedium,
                color = PortfolioTheme.colors.accent
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { skill.progress / 100f },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(4.dp)),
            color = PortfolioTheme.colors.accent,
            trackColor = PortfolioTheme.colors.surface,
        )
    }
}
