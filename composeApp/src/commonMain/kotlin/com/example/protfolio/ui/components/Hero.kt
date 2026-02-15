package com.example.protfolio.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme

@Composable
fun HeroSection(data: PortfolioResponse, windowSize: WindowSize) {
    // Reduce font size a bit as requested
    val taglineSize = if (windowSize == WindowSize.Compact) 40.sp else 64.sp
    val taglineLineHeight = if (windowSize == WindowSize.Compact) 48.sp else 72.sp
    val summarySize = if (windowSize == WindowSize.Compact) 18.sp else 24.sp
    val summaryLineHeight = if (windowSize == WindowSize.Compact) 28.sp else 36.sp
    val summaryWidth = if (windowSize == WindowSize.Compact) 1f else 0.7f

    Column {
        // Availability Status
        AvailabilityStatus(data.profile.availability)
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))

        val tagline = data.profile.tagline
        val highlight = data.profile.highlightKeyword
        
        val annotatedString = buildAnnotatedString {
            if (highlight.isNotEmpty() && tagline.contains(highlight, ignoreCase = true)) {
                val startIndex = tagline.indexOf(highlight, ignoreCase = true)
                val endIndex = startIndex + highlight.length
                
                append(tagline.substring(0, startIndex))
                withStyle(style = SpanStyle(color = PortfolioTheme.colors.accent)) {
                    append(tagline.substring(startIndex, endIndex))
                }
                append(tagline.substring(endIndex))
            } else {
                append(tagline)
            }
        }

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.displayLarge,
            fontSize = taglineSize,
            lineHeight = taglineLineHeight,
            fontWeight = FontWeight.Black,
            color = PortfolioTheme.colors.text,
            letterSpacing = (-2).sp
        )
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        
        // Social Icons - Bigger font
        Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
            val contacts = data.contacts
            HeroContactIcon(contacts.email.value, Icons.Default.Email)
            HeroContactIcon(contacts.github.value, Icons.Default.Code)
            HeroContactIcon(contacts.linkedin.value, Icons.Default.Work)
            HeroContactIcon(contacts.medium.value, Icons.Default.Article)
        }
    }
}

@Composable
fun HeroContactIcon(url: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    IconButton(onClick = { /* window.open(url) */ }) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PortfolioTheme.colors.text,
            modifier = Modifier.size(32.dp) // Bigger than footer (24.dp)
        )
    }
}

@Composable
fun AvailabilityStatus(status: String) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1000),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        )
    )

    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = PortfolioTheme.colors.accent.copy(alpha = 0.1f),
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = 1.dp,
                color = PortfolioTheme.colors.accent.copy(alpha = 0.2f),
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = PortfolioTheme.spacing.medium, vertical = PortfolioTheme.spacing.small)
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(PortfolioTheme.spacing.small)
                .background(
                    color = androidx.compose.ui.graphics.Color.Green.copy(alpha = alpha),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        Spacer(modifier = Modifier.width(PortfolioTheme.spacing.small))
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall,
            color = PortfolioTheme.colors.accent,
            fontWeight = FontWeight.Medium
        )
    }
}
