package com.example.protfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.profile_image
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Helper to extract emojis from a string (KMP compatible)
fun String.extractEmojis(): List<String> {
    val emojis = mutableListOf<String>()
    var i = 0
    while (i < this.length) {
        val ch = this[i]
        if (ch.isHighSurrogate() && i + 1 < this.length) {
            val low = this[i + 1]
            if (low.isLowSurrogate()) {
                val emoji = "$ch$low"
                // Basic check if it's in a common emoji range
                // (Most modern emojis are surrogate pairs starting with \uD83C, \uD83D, \uD83E)
                if (ch.code in 0xD83C..0xD83E) {
                    emojis.add(emoji)
                }
                i += 2
                continue
            }
        } else {
            // Check for single character emojis (like ⚡, ✨, etc.)
            if (ch.code in 0x2600..0x27BF || ch.code in 0x2300..0x23FF) {
                emojis.add(ch.toString())
            }
        }
        i++
    }
    return emojis.distinct()
}

@Composable
fun ProfileDetailSection(data: PortfolioResponse, windowSize: WindowSize) {
    val emojis = remember(data.profile.summary) {
        (data.profile.summary.primary + data.profile.summary.secondary).extractEmojis()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // 1. Primary Text - Always on Top, Full Width
        Text(
            text = data.profile.summary.primary,
            style = if (windowSize == WindowSize.Compact) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium,
            fontSize = if (windowSize == WindowSize.Compact) 18.sp else 32.sp,
            lineHeight = if (windowSize == WindowSize.Compact) 24.sp else 44.sp,
            fontWeight = if (windowSize == WindowSize.Compact) FontWeight.Bold else FontWeight.Black,
            fontFamily = FontFamily.Default,
            color = PortfolioTheme.colors.accent,
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (windowSize == WindowSize.Compact) TextAlign.Center else TextAlign.Start
        )

        Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))

        if (windowSize == WindowSize.Compact) {
            // Mobile (Compact): Primary -> Image -> Secondary
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage(emojis)
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                Text(
                    text = data.profile.summary.secondary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily.Default,
                    color = PortfolioTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                DownloadResumeButton(data.profile.resumeUrl)
            }
        } else {
            // Desktop/Tablet (Expanded): Row with Secondary (Left) and Image (Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PortfolioTheme.spacing.section)
            ) {
                // Left: Secondary Text + Button
                Column(modifier = Modifier.weight(1.2f)) {
                    Text(
                        text = data.profile.summary.secondary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        lineHeight = 36.sp,
                        fontFamily = FontFamily.Default,
                        color = PortfolioTheme.colors.secondaryText
                    )
                    
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))

                    DownloadResumeButton(data.profile.resumeUrl)
                }
               
                // Right: Image
                ProfileImage(emojis)
            }
        }
    }
}


@Composable
fun DownloadResumeButton(resumeUrl: String) {
    val uriHandler = LocalUriHandler.current
    Button(
        onClick = { uriHandler.openUri(resumeUrl) },
        modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PortfolioTheme.colors.surface),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PortfolioTheme.colors.border)
    ) {
        Text("DOWNLOAD RESUME", color = PortfolioTheme.colors.text, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun RowScope.ProfileImage(emojis: List<String>) {
    Box(modifier = Modifier.weight(0.8f), contentAlignment = Alignment.Center) {
        // Higher container to allow emojis to overflow slightly if needed
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // Circular Orbit Border
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, PortfolioTheme.colors.accent.copy(alpha = 0.2f), CircleShape)
            )

            // Orbiting Emojis
            emojis.forEachIndexed { index, emoji ->
                val angle = remember { Random.nextFloat() * 2 * PI.toFloat() }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            // Position on orbit (radius is 50% of parent width)
                            val radius = size.width / 2f
                            translationX = radius * cos(angle)
                            translationY = radius * sin(angle)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    EmojiBubble(emoji)
                }
            }

            AsyncImage(
                model = Res.drawable.profile_image,
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

@Composable
fun ColumnScope.ProfileImage(emojis: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f) // Compact size adjusted
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        // Circular Orbit Border
        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .border(1.dp, PortfolioTheme.colors.accent.copy(alpha = 0.2f), CircleShape)
        )

        // Orbiting Emojis
        emojis.forEachIndexed { index, emoji ->
            val angle = remember { Random.nextFloat() * 2 * PI.toFloat() }
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .graphicsLayer {
                        val radius = size.width / 2f
                        translationX = radius * cos(angle)
                        translationY = radius * sin(angle)
                    },
                contentAlignment = Alignment.Center
            ) {
                EmojiBubble(emoji)
            }
        }

        AsyncImage(
            model = Res.drawable.profile_image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(2.dp, PortfolioTheme.colors.accent, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun EmojiBubble(emoji: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, PortfolioTheme.colors.border.copy(alpha = 0.1f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp,
            fontFamily = FontFamily.Default // Essential for emoji rendering
        )
    }
}