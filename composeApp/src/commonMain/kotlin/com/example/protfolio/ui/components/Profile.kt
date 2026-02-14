package com.example.protfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.PortfolioTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
            Column(modifier = Modifier.weight(1.2f)) {
                 Text(
                    text = data.profile.summary.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    lineHeight = 34.sp,
                    color = PortfolioTheme.colors.text
                )
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.medium))
                 Text(
                    text = data.profile.summary.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    color = PortfolioTheme.colors.secondaryText
                )
            }
           
            AsyncImage(
                model = data.profile.profileImage,
                contentDescription = null,
                modifier = Modifier.weight(0.8f).aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
