package com.example.protfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.padding
import com.example.protfolio.model.FeaturedWorkResponse
import com.example.protfolio.theme.PortfolioTheme

@Composable
fun StaggeredProjectRow(project: FeaturedWorkResponse, isImageLeft: Boolean, windowSize: WindowSize) {
    if (windowSize == WindowSize.Compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ProjectImage(project.image, Modifier.fillMaxWidth())
            ProjectDetails(project, Modifier.fillMaxWidth(), TextAlign.Start, windowSize)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.section)
        ) {
            if (isImageLeft) {
                ProjectImage(project.image, Modifier.weight(1.2f))
                ProjectDetails(project, Modifier.weight(1f), TextAlign.Start, windowSize)
            } else {
                ProjectDetails(project, Modifier.weight(1f), TextAlign.End, windowSize)
                ProjectImage(project.image, Modifier.weight(1.2f))
            }
        }
    }
}

@Composable
fun ProjectImage(imageUrl: String, modifier: Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier.aspectRatio(1.5f).clip(RoundedCornerShape(2.dp)).background(
            PortfolioTheme.colors.surface),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProjectDetails(project: FeaturedWorkResponse, modifier: Modifier, alignment: TextAlign, windowSize: WindowSize) {
    Column(modifier = modifier, horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
        Text(text = project.industry.uppercase(), style = MaterialTheme.typography.labelMedium, color = PortfolioTheme.colors.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium)) // 12.dp -> medium (16) or small (8)? Using medium for better breathe
        Text(text = project.company, style = MaterialTheme.typography.displaySmall, color = PortfolioTheme.colors.text, fontSize = 42.sp, fontWeight = FontWeight.Bold, textAlign = alignment)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.small))
        Text(text = project.role, style = MaterialTheme.typography.titleMedium, color = PortfolioTheme.colors.secondaryText, fontSize = 20.sp, fontWeight = FontWeight.Medium, textAlign = alignment)
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        
        // Key Contributions
        project.keyContributions.take(2).forEach { contribution ->
            Text(text = "• $contribution", style = MaterialTheme.typography.bodyMedium, color = PortfolioTheme.colors.secondaryText, fontSize = 16.sp, lineHeight = 24.sp, textAlign = alignment)
            Spacer(modifier = Modifier.height(PortfolioTheme.spacing.extraSmall))
        }

        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        
        // Focus Areas & Impact
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            (project.focusAreas + project.impactMetrics).take(3).forEach { metric ->
                Column(horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
                    Text(metric.value, style = MaterialTheme.typography.headlineSmall, color = PortfolioTheme.colors.text, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    Text(metric.label.uppercase(), style = MaterialTheme.typography.labelSmall, color = PortfolioTheme.colors.secondaryText, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun ProjectsGridSection(grid: com.example.protfolio.model.ProjectsGridResponse, windowSize: WindowSize) {
    // 3x3 grid or responsive
    val columns = if (windowSize == WindowSize.Expanded) 3 else if (windowSize == WindowSize.Medium) 2 else 1
    
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        val chunkedProjects = grid.projects.chunked(columns)
        chunkedProjects.forEach { rowProjects ->
             Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                 rowProjects.forEach { project ->
                     ProjectGridItem(project, Modifier.weight(1f))
                 }
                 // Fill empty space if row is not full
                 repeat(columns - rowProjects.size) {
                     Spacer(modifier = Modifier.weight(1f))
                 }
             }
        }
    }
}

@Composable
fun ProjectGridItem(project: com.example.protfolio.model.ProjectItemResponse, modifier: Modifier) {
    Column(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .background(PortfolioTheme.colors.surface)
        .padding(PortfolioTheme.spacing.large)
    ) {
        Text(project.name, style = MaterialTheme.typography.headlineSmall, color = PortfolioTheme.colors.text, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.small))
        Text(project.description, style = MaterialTheme.typography.bodyMedium, color = PortfolioTheme.colors.secondaryText)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
        // Tech stack
        Row(horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.small)) {
            project.coreTech.take(3).forEach { tech ->
                 Text(tech, style = MaterialTheme.typography.labelSmall, color = PortfolioTheme.colors.accent, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
