package com.example.protfolio.model

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioResponse(
    val id: String,
    val profile: ProfileResponse,
    val featuredWork: List<FeaturedWorkResponse>,
    val expertise: ExpertiseResponse,
    val playground: List<PlaygroundItemResponse>,
    val philosophy: PhilosophyResponse,
    val connect: ConnectResponse,
    val metadata: MetadataResponse
)

@Serializable
data class ProfileResponse(
    val name: String,
    val title: String,
    val profileImage: String,
    val resumeUrl: String,
    val tagline: String,
    val summary: String,
    val availability: String,
    val contact: ContactResponse
)

@Serializable
data class ContactResponse(
    val email: String,
    val phone: String
)

@Serializable
data class FeaturedWorkResponse(
    val projectName: String,
    val sector: String,
    val role: String,
    val image: String,
    val impactMetrics: List<ImpactMetricResponse>,
    val description: String,
    val tags: List<String>
)

@Serializable
data class ImpactMetricResponse(
    val label: String,
    val value: String
)

@Serializable
data class ExpertiseResponse(
    val technical: List<TechnicalSkillResponse>,
    val domain: List<String>
)

@Serializable
data class TechnicalSkillResponse(
    val skill: String,
    val rating: Double
)

@Serializable
data class PlaygroundItemResponse(
    val title: String,
    val image: String,
    val description: String,
    val link: String
)

@Serializable
data class PhilosophyResponse(
    val title: String,
    val content: String
)

@Serializable
data class ConnectResponse(
    val title: String,
    val socials: List<SocialLinkResponse>
)

@Serializable
data class SocialLinkResponse(
    val platform: String,
    val url: String,
    val icon: String
)

@Serializable
data class MetadataResponse(
    val createdAt: String,
    val updatedAt: String,
    val version: String
)
