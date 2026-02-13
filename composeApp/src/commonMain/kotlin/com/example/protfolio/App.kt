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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import com.example.protfolio.models.PortfolioConfig
import com.example.protfolio.styles.AccentBlue
import com.example.protfolio.styles.NainiBlack
import com.example.protfolio.styles.NainiCard
import com.example.protfolio.styles.NainiMuted
import com.example.protfolio.styles.NainiWhite
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import protfolio.composeapp.generated.resources.Res
import protfolio.composeapp.generated.resources.ic_download
import protfolio.composeapp.generated.resources.letter_r

const val PROJECT_PLACEHOLDER = "https://images.unsplash.com/photo-1633356122544-f134324a6cee?q=80&w=2070"

@Composable
fun rememberPortfolioConfig(screenWidth: Dp): PortfolioConfig {
    val displayFont = FontFamily.SansSerif
    val bodyFont = FontFamily.SansSerif

    return when {
        // Desktop / Large Web
        screenWidth > 1200.dp -> PortfolioConfig(
            horizontalPadding = screenWidth * 0.15f,
            sectionSpacing = 140.dp,
            heroTitleSize = 82.sp,
            sectionTitleSize = 16.sp,
            bodySize = 22.sp,
            navFontSize = 16.sp,
            projectTitleSize = 48.sp,
            isMobile = false,
            isTablet = false,
            mainFont = bodyFont,
            displayFont = displayFont
        )
        // Laptop / Tablet
        screenWidth > 768.dp -> PortfolioConfig(
            horizontalPadding = 64.dp,
            sectionSpacing = 100.dp,
            heroTitleSize = 56.sp,
            sectionTitleSize = 14.sp,
            bodySize = 18.sp,
            navFontSize = 14.sp,
            projectTitleSize = 32.sp,
            isMobile = false,
            isTablet = true,
            mainFont = bodyFont,
            displayFont = displayFont
        )
        // Mobile
        else -> PortfolioConfig(
            horizontalPadding = 24.dp,
            sectionSpacing = 70.dp,
            heroTitleSize = 40.sp,
            sectionTitleSize = 12.sp,
            bodySize = 16.sp,
            navFontSize = 14.sp,
            projectTitleSize = 28.sp,
            isMobile = true,
            isTablet = false,
            mainFont = bodyFont,
            displayFont = displayFont
        )
    }
}

@Composable
fun App() {
    val apiService = PortfolioHttpService()
    val uiState = apiService.uiState.collectAsStateWithLifecycle()

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
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
    val activeIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(NainiBlack)) {
        val config = rememberPortfolioConfig(maxWidth)

        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                data = data,
                config = config,
                activeSectionIndex = activeIndex,
                onNavClick = { index ->
                    coroutineScope.launch { listState.animateScrollToItem(index) }
                },
                onDownloadResume = {}
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = config.horizontalPadding, vertical = 40.dp)
            ) {
                item {
                    HeroSection(data, config)
                    Spacer(modifier = Modifier.height(config.sectionSpacing))
                }

                item {
                    SectionTitle("PROFILE", config)
                    ProfileDetailSection(data, config)
                    Spacer(modifier = Modifier.height(config.sectionSpacing))
                }

                item { SectionTitle("SELECTED WORKS", config) }
                itemsIndexed(data.featuredWork) { index, project ->
                    StaggeredProjectRow(project = project, isImageLeft = index % 2 == 0, config = config)
                    Spacer(modifier = Modifier.height(config.sectionSpacing / 1.2f))
                }

                item {
                    SectionTitle("EXPERTISE", config)
                    SkillsFlow(data, config)
                    Spacer(modifier = Modifier.height(config.sectionSpacing))
                    FooterSection(data, config)
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    data: PortfolioResponse,
    config: PortfolioConfig,
    activeSectionIndex: Int,
    onNavClick: (Int) -> Unit,
    onDownloadResume: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NainiBlack.copy(alpha = 0.95f))
            .padding(horizontal = config.horizontalPadding, vertical = if (config.isMobile) 16.dp else 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.letter_r),
                contentDescription = null,
                modifier = Modifier.size(if (config.isMobile) 28.dp else 36.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = data.profile.name.uppercase(),
                color = NainiWhite,
                fontSize = config.navFontSize,
                fontFamily = config.displayFont, // Applied
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (config.isTablet) 24.dp else 40.dp)
        ) {
            if (!config.isMobile) {
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    val navItems = listOf("HOME" to 0, "PROFILE" to 1, "WORK" to 2)
                    navItems.forEach { (label, index) ->
                        val isActive = if (index == 2) activeSectionIndex >= 2 else activeSectionIndex == index
                        Text(
                            text = label,
                            color = if (isActive) AccentBlue else NainiMuted,
                            fontSize = config.navFontSize,
                            fontFamily = config.mainFont, // Applied
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onNavClick(index) }
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = onDownloadResume,
                border = androidx.compose.foundation.BorderStroke(1.dp, NainiWhite),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_download),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = NainiWhite
                )
                if (!config.isMobile) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RESUME",
                        color = NainiWhite,
                        fontSize = config.navFontSize,
                        fontFamily = config.mainFont, // Applied
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun HeroSection(data: PortfolioResponse, config: PortfolioConfig) {
    Column {
        Text(
            text = data.profile.tagline,
            fontSize = config.heroTitleSize,
            lineHeight = config.heroTitleSize * 1.1f,
            fontFamily = config.displayFont, // Applied
            fontWeight = FontWeight.ExtraBold,
            color = NainiWhite,
            letterSpacing = (-2).sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = data.profile.summary.substringBefore(".") + ".",
            fontSize = config.bodySize,
            fontFamily = config.mainFont, // Applied
            lineHeight = config.bodySize * 1.5f,
            color = NainiMuted,
            modifier = Modifier.fillMaxWidth(if (config.isMobile) 1f else 0.7f)
        )
    }
}

@Composable
fun ProfileDetailSection(data: PortfolioResponse, config: PortfolioConfig) {
    if (config.isMobile) {
        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
            AsyncImage(
                model = data.profile.profileImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().aspectRatio(1.2f).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = data.profile.summary,
                fontSize = config.bodySize,
                fontFamily = config.mainFont, // Applied
                lineHeight = config.bodySize * 1.6f,
                color = NainiWhite
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            Text(
                text = data.profile.summary,
                fontSize = config.bodySize,
                fontFamily = config.mainFont, // Applied
                lineHeight = config.bodySize * 1.6f,
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
}

@Composable
fun StaggeredProjectRow(project: FeaturedWorkResponse, isImageLeft: Boolean, config: PortfolioConfig) {
    if (config.isMobile) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ProjectImage(Modifier.fillMaxWidth())
            ProjectDetails(project, Modifier.fillMaxWidth(), TextAlign.Start, config)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (config.isTablet) 32.dp else 64.dp)
        ) {
            if (isImageLeft) {
                ProjectImage(Modifier.weight(1.2f))
                ProjectDetails(project, Modifier.weight(1f), TextAlign.Start, config)
            } else {
                ProjectDetails(project, Modifier.weight(1f), TextAlign.End, config)
                ProjectImage(Modifier.weight(1.2f))
            }
        }
    }
}

@Composable
fun ProjectImage(modifier: Modifier) {
    AsyncImage(
        model = PROJECT_PLACEHOLDER,
        contentDescription = null,
        modifier = modifier.aspectRatio(1.5f).clip(RoundedCornerShape(4.dp)).background(NainiCard),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProjectDetails(project: FeaturedWorkResponse, modifier: Modifier, alignment: TextAlign, config: PortfolioConfig) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End
    ) {
        Text(
            text = project.sector.uppercase(),
            color = AccentBlue,
            fontSize = 12.sp,
            fontFamily = config.displayFont, // Applied
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = project.projectName,
            color = NainiWhite,
            fontSize = config.projectTitleSize,
            fontFamily = config.displayFont, // Applied
            fontWeight = FontWeight.Bold,
            textAlign = alignment,
            lineHeight = config.projectTitleSize * 1.1f
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = project.description,
            color = NainiMuted,
            fontSize = config.bodySize * 0.9f,
            fontFamily = config.mainFont, // Applied
            lineHeight = config.bodySize * 1.4f,
            textAlign = alignment
        )

        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            project.impactMetrics.forEach { metric ->
                Column(horizontalAlignment = if (alignment == TextAlign.Start) Alignment.Start else Alignment.End) {
                    Text(
                        text = metric.value,
                        color = NainiWhite,
                        fontFamily = config.displayFont, // Applied
                        fontWeight = FontWeight.Black,
                        fontSize = config.bodySize * 1.2f
                    )
                    Text(
                        text = metric.label.uppercase(),
                        color = NainiMuted,
                        fontFamily = config.mainFont, // Applied
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, config: PortfolioConfig) {
    Text(
        text = title,
        color = NainiMuted,
        fontSize = config.sectionTitleSize,
        fontFamily = config.displayFont, // Applied
        fontWeight = FontWeight.Bold,
        letterSpacing = 4.sp,
        modifier = Modifier.padding(bottom = if (config.isMobile) 32.dp else 60.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlow(data: PortfolioResponse, config: PortfolioConfig) {
    val fontSize = if (config.isMobile) 24.sp else 36.sp
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(if (config.isMobile) 16.dp else 24.dp),
        verticalArrangement = Arrangement.spacedBy(if (config.isMobile) 16.dp else 24.dp)
    ) {
        data.expertise.technical.forEach { tech ->
            Text(
                text = tech.skill,
                color = NainiWhite,
                fontSize = fontSize,
                fontFamily = config.mainFont, // Applied
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun FooterSection(data: PortfolioResponse, config: PortfolioConfig) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = if (config.isMobile) 60.dp else 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.connect.title.uppercase(),
            color = AccentBlue,
            fontSize = 14.sp,
            fontFamily = config.displayFont, // Applied
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Let's build together.",
            color = NainiWhite,
            fontSize = if (config.isMobile) 32.sp else 64.sp,
            lineHeight = if (config.isMobile) 40.sp else 72.sp,
            fontFamily = config.displayFont, // Applied
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = data.profile.contact.email,
            color = NainiMuted,
            fontSize = config.bodySize,
            fontFamily = config.mainFont, // Applied
            fontWeight = FontWeight.Medium
        )
    }
}