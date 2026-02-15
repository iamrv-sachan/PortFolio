package com.example.protfolio.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.email
import protfolio.composeapp.generated.resources.github_logo
import protfolio.composeapp.generated.resources.linkedin_icon
import protfolio.composeapp.generated.resources.medium_icon

@Composable
fun HeroSection(data: PortfolioResponse, windowSize: WindowSize) {
    // Reduce font size a bit as requested
    val taglineSize = if (windowSize == WindowSize.Compact) 32.sp else 52.sp
    val taglineLineHeight = if (windowSize == WindowSize.Compact) 40.sp else 70.sp

    Column {
        // Availability Status
        AvailabilityStatus(data.profile.availability, windowSize)
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))

        val tagline = data.profile.tagline
        val highlight = data.profile.highlightKeyword
        
        val annotatedString = buildAnnotatedString {
            if (highlight.isNotEmpty() && tagline.contains(highlight, ignoreCase = true)) {
                val startIndex = tagline.indexOf(highlight, ignoreCase = true)
                val endIndex = startIndex + highlight.length
                
                append(tagline.take(startIndex))
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
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.doubleLarge))
        
        // Social Icons - Bigger font
        Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
            val contacts = data.contacts
            HeroContactIcon(url = contacts.linkedin.value, icon = Res.drawable.linkedin_icon)
            HeroContactIcon(url = contacts.github.value, icon = Res.drawable.github_logo)
            HeroContactIcon(url = contacts.email.value, icon = Res.drawable.email)
            HeroContactIcon(url = contacts.medium.value, icon = Res.drawable.medium_icon)
        }
    }
}

@Composable
fun HeroContactIcon(url: String, icon: DrawableResource) {
    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val tint = if (isHovered || isPressed) PortfolioTheme.colors.text else PortfolioTheme.colors.secondaryText

    IconButton(
        onClick = {
            uriHandler.openUri(url)
        },
        interactionSource = interactionSource
    ) {
        Icon(
            imageResource(icon),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun AvailabilityStatus(status: String, windowSize: WindowSize) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition()
    
    // Animation for the "breathing" glow effect (Outer Ring)
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2400
                1f at 0 with FastOutSlowInEasing
                1f at 1000 with FastOutSlowInEasing // Start expanding (Radiation Phase starts)
                4f at 2400 // Expand over 1.4s
            },
            repeatMode = RepeatMode.Restart
        )
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2400
                0f at 0 with FastOutSlowInEasing
                0f at 1000 with FastOutSlowInEasing // Start visible
                0.5f at 1200 with FastOutSlowInEasing // Fade in
                0f at 2400 // Fade out
            },
            repeatMode = RepeatMode.Restart
        )
    )
    
    // Inner Circle Size Animation (Shrink 1s, Inflate 1.4s)
    val innerCircleSize by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2400
                10f at 0 with FastOutSlowInEasing
                6f at 1000 with FastOutSlowInEasing // Shrink to 6dp over 1s
                10f at 2400 // Inflate back to 10dp over 1.4s
            },
            repeatMode = RepeatMode.Restart
        )
    )
    
    // Custom Green Colors
    val dotColor = androidx.compose.ui.graphics.Color(0xFF43A047) // Lighter Green (Shade 600)
    val lightGreen = androidx.compose.ui.graphics.Color(0xFF4CAF50) // Standard Green
    val glowColor = androidx.compose.ui.graphics.Color(0xFF81C784) // Even Lighter Green (Shade 300)
    val availabilityTextSize  = if (windowSize == WindowSize.Compact) 12.sp else 16.sp

    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = PortfolioTheme.spacing.medium, vertical = PortfolioTheme.spacing.small)
    ) {
        // Pulsating Dot Icon
        // Fixed size container to prevent text movement during animation
        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
            modifier = Modifier.size(16.dp) 
        ) {
            // Outer Glow (Expanding)
            Box(
                modifier = Modifier
                    .size(innerCircleSize.dp) 
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .background(
                        color = glowColor,
                        shape = CircleShape
                    )
            )
            // Inner Dot
            Box(
                modifier = Modifier
                    .size(innerCircleSize.dp)
                    .background(
                        color = dotColor,
                        shape = CircleShape
                    )
            )
        }
        
        Spacer(modifier = Modifier.width(PortfolioTheme.spacing.medium))
        
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium,
            color = lightGreen,
            fontSize = availabilityTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}
