package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.protfolio.ui.components.WindowSize

@Composable
fun ProfileDetailSection(data: PortfolioResponse, windowSize: WindowSize) {
    if (windowSize == WindowSize.Compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = data.profile.profileImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.8f).aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(PortfolioTheme.spacing.extraLarge))
            Text(
                text = data.profile.summary.primary,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                color = PortfolioTheme.colors.text
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.section)
        ) {
            // Left: Summary + Hobbies
            Column(modifier = Modifier.weight(1.2f)) {
                 Text(
                    text = data.profile.summary.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    lineHeight = 34.sp,
                    color = PortfolioTheme.colors.text
                )
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                
                // Hobbies / If not working
                Text(
                    text = "IF NOT CODING",
                    style = MaterialTheme.typography.labelMedium,
                    color = PortfolioTheme.colors.accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
                Row(horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.large)) {
                    HobbyItem("Badminton", Icons.Default.Star)
                    HobbyItem("Reading", Icons.Default.Favorite)
                    HobbyItem("Travel", Icons.Default.Flight)
                    HobbyItem("Music", Icons.Default.MusicNote)
                }
                
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                
                // Resume Button (Space bar style)
                DownloadResumeButton()
            }
           
            // Right: Round Image with Orbiting Icons
            Box(modifier = Modifier.weight(0.8f), contentAlignment = Alignment.Center) {
                // Circular Border with Icons (Mocking positions)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                        .border(1.dp, PortfolioTheme.colors.accent.copy(alpha = 0.2f), CircleShape)
                ) {
                   // Mock icons on orbit - simplified for now, just static positions
                }
                
                AsyncImage(
                    model = data.profile.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .border(2.dp, PortfolioTheme.colors.accent, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun HobbyItem(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = PortfolioTheme.colors.secondaryText, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, style = MaterialTheme.typography.bodySmall, color = PortfolioTheme.colors.secondaryText)
    }
}

@Composable
fun DownloadResumeButton() {
    Button(
        onClick = { /* Download */ },
        modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PortfolioTheme.colors.surface),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PortfolioTheme.colors.border)
    ) {
        Text("DOWNLOAD RESUME", color = PortfolioTheme.colors.text, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
    }
}
