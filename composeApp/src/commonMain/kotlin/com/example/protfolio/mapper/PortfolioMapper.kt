package com.example.protfolio.mapper

import com.example.protfolio.model.ContactItem
import com.example.protfolio.model.ContactItemResponse
import com.example.protfolio.model.Contacts
import com.example.protfolio.model.ContactsResponse
import com.example.protfolio.model.Expertise
import com.example.protfolio.model.ExpertiseResponse
import com.example.protfolio.model.FeaturedWork
import com.example.protfolio.model.FeaturedWorkResponse
import com.example.protfolio.model.LabelValue
import com.example.protfolio.model.LabelValueResponse
import com.example.protfolio.model.MetadataResponse
import com.example.protfolio.model.MetadataTime
import com.example.protfolio.model.PortfolioData
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.model.Profile
import com.example.protfolio.model.ProfileResponse
import com.example.protfolio.model.ProfileSummary
import com.example.protfolio.model.ProfileSummaryResponse
import com.example.protfolio.model.ProjectItem
import com.example.protfolio.model.ProjectItemResponse
import com.example.protfolio.model.ProjectsGrid
import com.example.protfolio.model.ProjectsGridResponse
import com.example.protfolio.model.TechnicalSkill
import com.example.protfolio.model.TechnicalSkillResponse

fun PortfolioData.toResponse(): PortfolioResponse =
    PortfolioResponse(
        id = id.oid,
        profile = profile.toResponse(),
        contacts = contacts.toResponse(),
        featuredWork = featuredWork.map { it.toResponse() },
        projectsGrid = projectsGrid.toResponse(),
        expertise = expertise.toResponse(),
        metadata = metadata.toResponse()
    )

fun Profile.toResponse() = ProfileResponse(
    name = name,
    title = title,
    profileImage = profileImage,
    resumeUrl = resumeUrl,
    tagline = tagline,
    highlightKeyword = highlightKeyword,
    summary = summary.toResponse(),
    availability = availability
)

fun ProfileSummary.toResponse() = ProfileSummaryResponse(
    primary = primary,
    secondary = secondary
)

fun Contacts.toResponse() = ContactsResponse(
    email = email.toResponse(),
    phone = phone.toResponse(),
    github = github.toResponse(),
    linkedin = linkedin.toResponse(),
    medium = medium.toResponse()
)

fun ContactItem.toResponse() = ContactItemResponse(
    value = value,
    icon = icon
)

fun FeaturedWork.toResponse() = FeaturedWorkResponse(
    company = company,
    industry = industry,
    role = role,
    duration = duration,
    image = image,
    focusAreas = focusAreas.map { it.toResponse() },
    impactMetrics = impactMetrics.map { it.toResponse() },
    keyContributions = keyContributions
)

fun LabelValue.toResponse() = LabelValueResponse(
    label = label,
    value = value
)

fun ProjectsGrid.toResponse() = ProjectsGridResponse(
    layout = layout,
    projects = projects.map { it.toResponse() }
)

fun ProjectItem.toResponse() = ProjectItemResponse(
    name = name,
    repo = repo,
    coreTech = coreTech,
    description = description,
    highlights = highlights.map { it.toResponse() }
)

fun Expertise.toResponse() = ExpertiseResponse(
    technical = technical.map { it.toResponse() }
)

fun TechnicalSkill.toResponse() = TechnicalSkillResponse(
    skill = skill,
    progress = progress
)

fun MetadataTime.toResponse() = MetadataResponse(
    updatedAt = updatedAt.date,
    version = version
)
