package com.example.protfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.theme.AppTheme
import com.example.protfolio.theme.PortfolioTheme
import com.example.protfolio.ui.components.FooterSection
import com.example.protfolio.ui.components.HeaderSection
import com.example.protfolio.ui.components.HeroSection
import com.example.protfolio.ui.components.ProfileDetailSection
import com.example.protfolio.ui.components.SectionTitle
import com.example.protfolio.ui.components.SkillsFlow
import com.example.protfolio.ui.components.ProjectsGridSection
import com.example.protfolio.ui.components.StaggeredProjectRow
import com.example.protfolio.ui.components.WindowSize
import kotlinx.coroutines.launch

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

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = PortfolioTheme.colors.background) {
            when (val state = uiState.value) {
                is PortfolioUiState.SuccessData -> PortfolioScreen(state.data)
                is PortfolioUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PortfolioTheme.colors.text)
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
    val themeController = PortfolioTheme.controller

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(PortfolioTheme.colors.background)) {
        val screenWidth = maxWidth
        val windowSize = when {
            screenWidth < 600.dp -> WindowSize.Compact
            screenWidth < 840.dp -> WindowSize.Medium
            else -> WindowSize.Expanded
        }
        
        val horizontalPadding = when (windowSize) {
            WindowSize.Compact -> PortfolioTheme.spacing.medium
            WindowSize.Medium -> PortfolioTheme.spacing.extraLarge
            WindowSize.Expanded -> screenWidth * 0.12f
        }
        
        val uriHandler = LocalUriHandler.current

        // Calculate section indices for navigation
        // 0: Hero
        // 1: Selected Works Title
        val worksIndex = 2
        // 1 + works count + 1 (divider)
        val projectsIndex = worksIndex + data.featuredWork.size + 1
        // projectsIndex + 1 (Title) + 1 (Grid) + 1 (Divider)
        val profileIndex = projectsIndex + 2
        
        // Navigation Map
        val navIndices = mapOf(
            "HOME" to 0,
            "WORK" to worksIndex,
            "PROJECTS" to projectsIndex,
            "ABOUT" to profileIndex
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // --- STICKY HEADER ---
            HeaderSection(
                data = data,
                horizontalPadding = horizontalPadding,
                activeSectionIndex = activeIndex,
                navIndices = navIndices,
                onNavClick = { index ->
                    coroutineScope.launch { listState.animateScrollToItem(index) }
                },
                onDownloadResume = {
                    uriHandler.openUri("resume.pdf")
                },
                onToggleTheme = { themeController.toggle() },
                isDarkTheme = themeController.isDark,
                windowSize = windowSize
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
            ) {
                // Section 0: HERO
                item {
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        HeroSection(data, windowSize)
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                }

                item {
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                         HorizontalDivider(color = PortfolioTheme.colors.border, thickness = 1.dp)
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                }

                item {
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        SectionTitle("CAREER JOURNEY")
                    }
                }
                itemsIndexed(data.featuredWork) { index, project ->
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        StaggeredProjectRow(
                            project = project, 
                            isImageLeft = index % 2 == 0, 
                            windowSize = windowSize,
                            listState = listState,
                            index = index + 3,
                        )
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                }


                // Section 2: PROJECTS
                item {
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        Column {
                            SectionTitle("PROJECTS")
                            ProjectsGridSection(data.projectsGrid, windowSize)
                        }
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        HorizontalDivider(color = PortfolioTheme.colors.border, thickness = 1.dp)
                    }
                }

                // Section 3: EXPERTISE
                item {
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        Column {
                            SectionTitle("EXPERTISE")
                            SkillsFlow(data, windowSize)
                        }
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        HorizontalDivider(color = PortfolioTheme.colors.border, thickness = 1.dp)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        Column {
                            SectionTitle("ABOUT")
                            ProfileDetailSection(data, windowSize)
                        }
                    }
                    Spacer(modifier = Modifier.height(PortfolioTheme.spacing.section))
                }

                // Section 5: FOOTER (Lets Build Together)
                item {
                    FooterSection(data, windowSize)
                }
            }
        }
    }
}