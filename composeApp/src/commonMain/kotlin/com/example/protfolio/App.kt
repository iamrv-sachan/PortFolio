package com.example.protfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import com.example.protfolio.model.FeaturedWorkResponse
import com.example.protfolio.model.PortfolioResponse
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.letter_r

// --- Premium Figma Dark Palette ---
val NainiBlack = Color(0xFF0A0A0A)
val NainiCard = Color(0xFF141414)
val NainiWhite = Color(0xFFF5F5F5)
val NainiMuted = Color(0xFF888888)
val NainiBorder = Color(0xFF262626)
val AccentBlue = Color(0xFF2196F3)

// Hardcoded image as requested, but all text is dynamic
const val PROJECT_PLACEHOLDER = "https://images.unsplash.com/photo-1633356122544-f134324a6cee?q=80&w=2070"


@Composable
fun App() {
    val apiService = PortfolioHttpService()
    val uiState = apiService.uiState.collectAsStateWithLifecycle()

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .crossfade(true)
            .build()
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = NainiBlack) {
            when (val state = uiState.value) {
                is PortfolioUiState.SuccessData -> PortfolioScreen(state.data)
                is PortfolioUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = NainiWhite)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun PortfolioScreen(data: PortfolioResponse) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Highlighting logic based on scroll index
    val activeIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(NainiBlack)) {
        val screenWidth = maxWidth
        val horizontalPadding = screenWidth * 0.2f

        Column(modifier = Modifier.fillMaxSize()) {
            // --- STICKY HEADER ---
            HeaderSection(
                data = data,
                horizontalPadding = horizontalPadding,
                activeSectionIndex = activeIndex,
                onNavClick = { index ->
                    coroutineScope.launch { listState.animateScrollToItem(index) }
                },
                onDownloadResume = {
//                    window.open(data.profile.resumeUrl, "_blank")
                }
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 40.dp)
            ) {
                // Section 0: HERO
                item {
                    HeroSection(data)
                    Spacer(modifier = Modifier.height(120.dp))
                }

                // Section 1: PROFILE
                item {
                    SectionTitle("PROFILE")
                    ProfileDetailSection(data)
                    Spacer(modifier = Modifier.height(120.dp))
                }

                // Section 2+: WORKS
                item { SectionTitle("SELECTED WORKS") }
                itemsIndexed(data.featuredWork) { index, project ->
                    StaggeredProjectRow(project = project, isImageLeft = index % 2 == 0)
                    Spacer(modifier = Modifier.height(100.dp))
                }

                // EXPERTISE & FOOTER
                item {
                    SectionTitle("EXPERTISE")
                    SkillsFlow(data)
                    Spacer(modifier = Modifier.height(120.dp))
                    FooterSection(data)
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    data: PortfolioResponse,
    horizontalPadding: androidx.compose.ui.unit.Dp,
    activeSectionIndex: Int,
    onNavClick: (Int) -> Unit,
    onDownloadResume: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NainiBlack.copy(alpha = 0.9f))
            .padding(horizontal = horizontalPadding, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.letter_r),
                contentDescription = null,
                modifier = Modifier.size(36.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = data.profile.name.uppercase(),
                color = NainiWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        }

        // Center: Web Navigation (High-end spaced typography)
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            val navItems = listOf("HOME" to 0, "PROFILE" to 1, "WORK" to 2)
            navItems.forEach { (label, index) ->
                val isActive = if (index == 2) activeSectionIndex >= 2 else activeSectionIndex == index
                Text(
                    text = label,
                    color = if (isActive) AccentBlue else NainiMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavClick(index) }
                )
            }
        }

        // Right: Resume CTA
        OutlinedButton(
            onClick = onDownloadResume,
            border = androidx.compose.foundation.BorderStroke(1.dp, NainiWhite),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("RESUME", color = NainiWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileDetailSection(data: PortfolioResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        Text(
            text = data.profile.summary,
            fontSize = 20.sp,
            lineHeight = 34.sp,
            color = NainiWhite,
            modifier = Modifier.weight(1.2f)
        )
        AsyncImage(
            model = data.profile.profileImage,
            contentDescription = null,
            modifier = Modifier.weight(0.8f).aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HeroSection(data: PortfolioResponse) {
    Column {
        Text(
            text = data.profile.tagline,
            fontSize = 72.sp,
            lineHeight = 80.sp,
            fontWeight = FontWeight.ExtraBold,
            color = NainiWhite,
            letterSpacing = (-3).sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = data.profile.summary.substringBefore(".") + ".", // Short intro for hero
            fontSize = 24.sp,
            lineHeight = 36.sp,
            color = NainiMuted,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Composable
fun StaggeredProjectRow(project: FeaturedWorkResponse, isImageLeft: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        if (isImageLeft) {
            ProjectImage(Modifier.weight(1.2f))
            ProjectDetails(project, Modifier.weight(1f), TextAlign.Start)
        } else {
            ProjectDetails(project, Modifier.weight(1f), TextAlign.End)
            ProjectImage(Modifier.weight(1.2f))
        }
    }
}

@Composable
fun ProjectImage(modifier: Modifier) {
    AsyncImage(
        model = PROJECT_PLACEHOLDER,
        contentDescription = null,
        modifier = modifier.aspectRatio(1.5f).clip(RoundedCornerShape(2.dp)).background(NainiCard),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProjectDetails(project: FeaturedWorkResponse, modifier: Modifier, alignment: TextAlign) {
    Column(modifier = modifier, horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
        Text(text = project.sector.uppercase(), color = AccentBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = project.projectName, color = NainiWhite, fontSize = 42.sp, fontWeight = FontWeight.Bold, textAlign = alignment)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = project.description, color = NainiMuted, fontSize = 18.sp, lineHeight = 28.sp, textAlign = alignment)

        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            project.impactMetrics.forEach { metric ->
                Column(horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
                    Text(metric.value, color = NainiWhite, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Text(metric.label.uppercase(), color = NainiMuted, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, color = NainiMuted, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 4.sp, modifier = Modifier.padding(bottom = 60.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlow(data: PortfolioResponse) {
    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        data.expertise.technical.forEach { tech ->
            Text(text = tech.skill, color = NainiWhite, fontSize = 36.sp, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun FooterSection(data: PortfolioResponse) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = data.connect.title.uppercase(), color = AccentBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Let's build together.", color = NainiWhite, fontSize = 64.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = data.profile.contact.email, color = NainiMuted, fontSize = 24.sp, fontWeight = FontWeight.Medium)
    }
}