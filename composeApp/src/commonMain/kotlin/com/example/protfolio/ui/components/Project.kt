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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import com.example.protfolio.model.FeaturedWorkResponse
import com.example.protfolio.theme.PortfolioTheme

@Composable
fun StaggeredProjectRow(project: FeaturedWorkResponse, isImageLeft: Boolean, windowSize: WindowSize) {
    // Card-like container with click behavior
    val containerModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(PortfolioTheme.colors.surface)
        .clickable { /* Navigate to project.url if available */ }
    
    if (windowSize == WindowSize.Compact) {
        Column(modifier = containerModifier) {
            ProjectImage(project.image, Modifier.fillMaxWidth().height(240.dp), isEdgeToEdge = true)
            ProjectDetails(project, Modifier.fillMaxWidth().padding(PortfolioTheme.spacing.large), TextAlign.Start, windowSize)
        }
    } else {
        Row(modifier = containerModifier) {
            if (isImageLeft) {
                ProjectImage(project.image, Modifier.weight(1.2f).height(400.dp), isEdgeToEdge = true)
                ProjectDetails(project, Modifier.weight(1f).padding(PortfolioTheme.spacing.large), TextAlign.Start, windowSize)
            } else {
                // Image on right, content on left. Content should be Start aligned as per "For image on right, keep content startToLeft"
                ProjectDetails(project, Modifier.weight(1f).padding(PortfolioTheme.spacing.large), TextAlign.Start, windowSize)
                ProjectImage(project.image, Modifier.weight(1.2f).height(400.dp), isEdgeToEdge = true)
            }
        }
    }
}

@Composable
fun ProjectImage(imageUrl: String, modifier: Modifier, isEdgeToEdge: Boolean = false) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .let { if (!isEdgeToEdge) it.clip(RoundedCornerShape(2.dp)) else it } // No rounded corners if edge-to-edge (handled by container clip)
            .background(PortfolioTheme.colors.surface),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProjectDetails(project: FeaturedWorkResponse, modifier: Modifier, alignment: TextAlign, windowSize: WindowSize) {
    Column(modifier = modifier, horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
        
        // Logo and Company Name Row
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Placeholder Logo
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(PortfolioTheme.colors.accent)
            )
            Text(text = project.company, style = MaterialTheme.typography.headlineMedium, color = PortfolioTheme.colors.text, fontWeight = FontWeight.Bold, textAlign = alignment)
        }
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.small))
        Text(text = project.industry.uppercase(), style = MaterialTheme.typography.labelMedium, color = PortfolioTheme.colors.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        
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
        .clip(RoundedCornerShape(12.dp))
        .border(1.dp, PortfolioTheme.colors.border, RoundedCornerShape(12.dp))
        .background(Color.Transparent) // No background color
        .clickable { /* Navigate */ }
        .padding(PortfolioTheme.spacing.large)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Text(project.name, style = MaterialTheme.typography.headlineSmall, color = PortfolioTheme.colors.text, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ArrowOutward, contentDescription = null, tint = PortfolioTheme.colors.accent, modifier = Modifier.size(20.dp))
        }
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.small))
        Text(project.description, style = MaterialTheme.typography.bodyMedium, color = PortfolioTheme.colors.secondaryText, minLines = 3, maxLines = 3) // Consistent height for desc
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
        
        // Tech stack
        Row(horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.small)) {
            project.coreTech.take(3).forEach { tech ->
                 Text(
                     text = tech, 
                     style = MaterialTheme.typography.labelSmall, 
                     color = PortfolioTheme.colors.accent, 
                     fontWeight = FontWeight.SemiBold,
                     modifier = Modifier
                         .border(1.dp, PortfolioTheme.colors.accent.copy(alpha = 0.5f), RoundedCornerShape(50))
                         .padding(horizontal = 8.dp, vertical = 4.dp)
                 )
            }
        }
    }
}
