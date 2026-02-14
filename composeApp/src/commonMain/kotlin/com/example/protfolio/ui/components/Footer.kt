package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = PortfolioTheme.spacing.sectionLarge), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "CONNECT", style = MaterialTheme.typography.labelMedium, color = PortfolioTheme.colors.accent, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.large))
        Text(text = "Let's build together.", style = MaterialTheme.typography.displayMedium, color = PortfolioTheme.colors.text, fontSize = titleSize, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.doubleLarge))
        
        // Contacts Row
        Row(horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
            val contacts = data.contacts
            ContactIcon(contacts.email.value, Icons.Default.Email)
            ContactIcon(contacts.github.value, Icons.Default.Code) // Placeholder for Github
            ContactIcon(contacts.linkedin.value, Icons.Default.Work) // Placeholder for LinkedIn
            ContactIcon(contacts.medium.value, Icons.Default.Article) // Placeholder for Medium
        }
    }
}

@Composable
fun ContactIcon(url: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    androidx.compose.material3.IconButton(onClick = { /* window.open(url) */ }) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PortfolioTheme.colors.text,
            modifier = Modifier.size(PortfolioTheme.spacing.large)
        )
    }
}
