package com.example.protfolio.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.protfolio.theme.PortfolioTheme
import org.jetbrains.compose.resources.imageResource
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.email
import protfolio.composeapp.generated.resources.github_logo
import protfolio.composeapp.generated.resources.linkedin_icon
import protfolio.composeapp.generated.resources.medium_icon

enum class SocialMedia {
    LINKEDIN, GITHUB, EMAIL, MEDIUM
}

@Composable
fun SocialContactIcon(
    media: SocialMedia, 
    url: String, 
    windowSize: WindowSize,
    modifier: Modifier = Modifier.size(36.dp)
) {
    val icon = when (media) {
        SocialMedia.LINKEDIN -> Res.drawable.linkedin_icon
        SocialMedia.GITHUB -> Res.drawable.github_logo
        SocialMedia.EMAIL -> Res.drawable.email
        SocialMedia.MEDIUM -> Res.drawable.medium_icon
    }

    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val tint = if (windowSize != WindowSize.Expanded) {
        PortfolioTheme.colors.text
    } else if (isHovered || isPressed) {
        when (media) {
            SocialMedia.LINKEDIN -> com.example.protfolio.theme.LinkedInBlue
            SocialMedia.EMAIL -> com.example.protfolio.theme.EmailRed
            else -> PortfolioTheme.colors.text
        }
    } else {
        PortfolioTheme.colors.secondaryText
    }

    IconButton(
        onClick = { uriHandler.openUri(url) },
        interactionSource = interactionSource,
        modifier = modifier
    ) {
        Icon(
            imageResource(icon),
            contentDescription = media.name,
            tint = tint,
            modifier = Modifier.size(36.dp)
        )
    }
}
