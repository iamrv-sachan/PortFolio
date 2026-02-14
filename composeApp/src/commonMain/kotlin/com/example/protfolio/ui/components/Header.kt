package com.example.protfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme
import org.jetbrains.compose.resources.painterResource
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.letter_r

@Composable
fun HeaderSection(
    data: PortfolioResponse,
    horizontalPadding: Dp,
    activeSectionIndex: Int,
    navIndices: Map<String, Int>,
    onNavClick: (Int) -> Unit,
    onDownloadResume: () -> Unit,
    onToggleTheme: () -> Unit,
    isDarkTheme: Boolean,
    windowSize: WindowSize
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PortfolioTheme.colors.background.copy(alpha = 0.95f)) // Slightly more opaque
            .padding(horizontal = horizontalPadding, vertical = PortfolioTheme.spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.letter_r),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(CircleShape) // Slightly larger
            )
            Spacer(modifier = Modifier.width(PortfolioTheme.spacing.medium))
            if (windowSize != WindowSize.Compact) {
                Text(
                    text = data.profile.name.uppercase(),
                    color = PortfolioTheme.colors.text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Right: Nav + Resume + Theme
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.medium)
        ) {
            // 1. Navigation Items
            if (windowSize != WindowSize.Compact) {
                val navItems = listOf("HOME", "WORK", "PROJECTS", "EXPERTISE")
                navItems.forEach { label ->
                    val targetIndex = navIndices[label] ?: 0
                    // Active state logic: simple approximation
                    // If activeSectionIndex is >= targetIndex and < nextTargetIndex
                    
                    // Simplified active state for now: match strict index or range
                    val isActive = when (label) {
                        "HOME" -> activeSectionIndex == 0
                        "WORK" -> activeSectionIndex >= targetIndex && activeSectionIndex < (navIndices["PROJECTS"] ?: Int.MAX_VALUE)
                        "PROJECTS" -> activeSectionIndex >= targetIndex && activeSectionIndex < (navIndices["EXPERTISE"] ?: Int.MAX_VALUE)
                        "EXPERTISE" -> activeSectionIndex >= targetIndex
                        else -> false
                    }
                    
                    val backgroundColor = if (isActive) PortfolioTheme.colors.surface else Color.Transparent
                    
                    Text(
                        text = label,
                        color = if (isActive) PortfolioTheme.colors.text else PortfolioTheme.colors.secondaryText,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                            .clickable { onNavClick(targetIndex) }
                            .padding(horizontal = PortfolioTheme.spacing.medium, vertical = PortfolioTheme.spacing.small)
                    )
                }
                Spacer(modifier = Modifier.width(PortfolioTheme.spacing.medium))
            }

            // 2. Resume Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(PortfolioTheme.colors.surface)
                    .border(1.dp, PortfolioTheme.colors.border, RoundedCornerShape(8.dp))
                    .clickable(onClick = onDownloadResume)
                    .padding(horizontal = PortfolioTheme.spacing.medium, vertical = PortfolioTheme.spacing.small)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = PortfolioTheme.colors.text,
                        modifier = Modifier.size(PortfolioTheme.spacing.medium)
                    )
                    Spacer(modifier = Modifier.width(PortfolioTheme.spacing.small))
                    Text(
                        "RESUME",
                        color = PortfolioTheme.colors.text,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 3. Theme Toggle
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.BrightnessHigh else Icons.Default.BrightnessLow,
                    contentDescription = "Toggle Theme",
                    tint = PortfolioTheme.colors.text
                )
            }
        }
    }
}
