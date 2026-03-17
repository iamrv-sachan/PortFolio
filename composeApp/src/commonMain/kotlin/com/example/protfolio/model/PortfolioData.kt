package com.example.protfolio.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    @SerialName("_id")
    val id: ObjectId,

    val profile: Profile,

    val contacts: Contacts,

    @SerialName("featured_work")
    val featuredWork: List<FeaturedWork>,

    @SerialName("projects_grid")
    val projectsGrid: ProjectsGrid,

    val expertise: Expertise,

    val metadata: MetadataTime
)

@Serializable
data class ObjectId(
    @SerialName("\$oid")
    val oid: String
)

@Serializable
data class Profile(
    val name: String,
    val title: String,

    @SerialName("profile_image")
    val profileImage: String,

    @SerialName("resume_url")
    val resumeUrl: String,

    val tagline: String,

    @SerialName("highlight_keyword")
    val highlightKeyword: String,

    val summary: ProfileSummary,

    val availability: String
)

@Serializable
data class ProfileSummary(
    val primary: String,
    val secondary: String
)

@Serializable
data class Contacts(
    val email: ContactItem,
    val phone: ContactItem,
    val github: ContactItem,
    val linkedin: ContactItem,
    val medium: ContactItem
)

@Serializable
data class ContactItem(
    val value: String,
    val icon: String
)

@Serializable
data class FeaturedWork(
    val company: String,
    val industry: String,
    val role: String,
    val duration: String,
    val image: String,

    @SerialName("focus_areas")
    val focusAreas: List<LabelValue>,

    @SerialName("impact_metrics")
    val impactMetrics: List<LabelValue>,

    @SerialName("key_contributions")
    val keyContributions: List<String>
)

@Serializable
data class LabelValue(
    val label: String,
    val value: String
)

@Serializable
data class ProjectsGrid(
    val layout: String,
    val projects: List<ProjectItem>
)

@Serializable
data class ProjectItem(
    val name: String,
    val repo: String,

    @SerialName("core_tech")
    val coreTech: List<String>,

    val description: String,
    val highlights: List<LabelValue>
)

@Serializable
data class Expertise(
    val technical: List<TechnicalSkill>
)

@Serializable
data class TechnicalSkill(
    val skill: String,
    val progress: Int
)

@Serializable
data class MetadataTime(
    @SerialName("updatedAt")
    val updatedAt: DateObj,

    val version: String
)

@Serializable
data class DateObj(
    @SerialName("\$date")
    val date: String
)
