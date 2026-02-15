package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.protfolio.ui.components.SocialMedia
import com.example.protfolio.ui.components.SocialContactIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme

import com.example.protfolio.ui.components.WindowSize

@Composable
fun FooterSection(data: PortfolioResponse, windowSize: WindowSize) {
    val titleSize = if (windowSize == WindowSize.Compact) 32.sp else 64.sp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PortfolioTheme.colors.surface) // Darker background
            .padding(vertical = PortfolioTheme.spacing.section),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "THANKS FOR STOPPING BY!", style = MaterialTheme.typography.labelMedium, color = PortfolioTheme.colors.accent, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        Text(text = "Let's connect to build better things.", style = MaterialTheme.typography.displayMedium, color = PortfolioTheme.colors.text, fontSize = titleSize, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.doubleLarge))
        
        // Contacts Row
        Row(horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
            val contacts = data.contacts
            SocialContactIcon(media = SocialMedia.EMAIL, url = contacts.email.value, windowSize = windowSize)
            SocialContactIcon(media = SocialMedia.GITHUB, url = contacts.github.value, windowSize = windowSize)
            SocialContactIcon(media = SocialMedia.LINKEDIN, url = contacts.linkedin.value, windowSize = windowSize)
            SocialContactIcon(media = SocialMedia.MEDIUM, url = contacts.medium.value, windowSize = windowSize)
        }
        
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
        
        Text(
            text = "© 2026 Rajeev Sachan",
            style = MaterialTheme.typography.bodySmall,
            color = PortfolioTheme.colors.secondaryText
        )
    }
}

