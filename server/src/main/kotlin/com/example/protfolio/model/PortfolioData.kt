package com.example.protfolio.model

import java.time.Instant
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class PortfolioData(
    @BsonProperty("_id")
    val id: ObjectId,

    val profile: Profile,

    val contacts: Contacts,

    @BsonProperty("featured_work")
    val featuredWork: List<FeaturedWork>,

    @BsonProperty("projects_grid")
    val projectsGrid: ProjectsGrid,

    val expertise: Expertise,

    val metadata: MetadataTime
)


// -------------------- PROFILE --------------------

data class Profile(
    val name: String,
    val title: String,

    @BsonProperty("profile_image")
    val profileImage: String,

    @BsonProperty("resume_url")
    val resumeUrl: String,

    val tagline: String,

    @BsonProperty("highlight_keyword")
    val highlightKeyword: String,

    val summary: ProfileSummary,

    val availability: String
)

data class ProfileSummary(
    val primary: String,
    val secondary: String
)


// -------------------- CONTACTS --------------------

data class Contacts(
    val email: ContactItem,
    val phone: ContactItem,
    val github: ContactItem,
    val linkedin: ContactItem,
    val medium: ContactItem
)

data class ContactItem(
    val value: String,
    val icon: String
)


// -------------------- FEATURED WORK --------------------

data class FeaturedWork(
    val company: String,
    val industry: String,
    val role: String,
    val duration: String,
    val image: String,

    @BsonProperty("focus_areas")
    val focusAreas: List<LabelValue>,

    @BsonProperty("impact_metrics")
    val impactMetrics: List<LabelValue>,

    @BsonProperty("key_contributions")
    val keyContributions: List<String>
)

data class LabelValue(
    val label: String,
    val value: String
)


// -------------------- PROJECT GRID --------------------

data class ProjectsGrid(
    val layout: String,
    val projects: List<ProjectItem>
)

data class ProjectItem(
    val name: String,
    val repo: String,

    @BsonProperty("core_tech")
    val coreTech: List<String>,

    val description: String,
    val highlights: List<LabelValue>
)


// -------------------- EXPERTISE --------------------

data class Expertise(
    val technical: List<TechnicalSkill>
)

data class TechnicalSkill(
    val skill: String,
    val progress: Int
)


// -------------------- METADATA --------------------

data class MetadataTime(
    @BsonProperty("updatedAt")
    val updatedAt: Instant,

    val version: String
)
