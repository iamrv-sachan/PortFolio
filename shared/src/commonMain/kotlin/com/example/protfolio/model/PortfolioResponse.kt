package com.example.protfolio.model

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioResponse(
    val id: String,
    val profile: ProfileResponse,
    val contacts: ContactsResponse,
    val featuredWork: List<FeaturedWorkResponse>,
    val projectsGrid: ProjectsGridResponse,
    val expertise: ExpertiseResponse,
    val metadata: MetadataResponse
)

@Serializable
data class ProfileResponse(
    val name: String,
    val title: String,
    val profileImage: String,
    val resumeUrl: String,
    val tagline: String,
    val highlightKeyword: String,
    val summary: ProfileSummaryResponse,
    val availability: String
)

@Serializable
data class ProfileSummaryResponse(
    val primary: String,
    val secondary: String
)

@Serializable
data class ContactsResponse(
    val email: ContactItemResponse,
    val phone: ContactItemResponse,
    val github: ContactItemResponse,
    val linkedin: ContactItemResponse,
    val medium: ContactItemResponse
)

@Serializable
data class ContactItemResponse(
    val value: String,
    val icon: String
)

@Serializable
data class FeaturedWorkResponse(
    val company: String,
    val industry: String,
    val role: String,
    val duration: String,
    val image: String,
    val focusAreas: List<LabelValueResponse>,
    val impactMetrics: List<LabelValueResponse>,
    val keyContributions: List<String>
)

@Serializable
data class LabelValueResponse(
    val label: String,
    val value: String
)

@Serializable
data class ProjectsGridResponse(
    val layout: String,
    val projects: List<ProjectItemResponse>
)

@Serializable
data class ProjectItemResponse(
    val name: String,
    val repo: String,
    val coreTech: List<String>,
    val description: String,
    val highlights: List<LabelValueResponse>
)

@Serializable
data class ExpertiseResponse(
    val technical: List<TechnicalSkillResponse>
)

@Serializable
data class TechnicalSkillResponse(
    val skill: String,
    val progress: Int
)

@Serializable
data class MetadataResponse(
    val updatedAt: String,
    val version: String
)
