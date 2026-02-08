package com.example.protfolio.mapper

import Connect
import Contact
import Expertise
import FeaturedWork
import ImpactMetric
import MetadataTime
import Philosophy
import PlaygroundItem
import PortfolioData
import Profile
import SocialLink
import TechnicalSkill
import com.example.protfolio.model.ConnectResponse
import com.example.protfolio.model.ContactResponse
import com.example.protfolio.model.ExpertiseResponse
import com.example.protfolio.model.FeaturedWorkResponse
import com.example.protfolio.model.ImpactMetricResponse
import com.example.protfolio.model.MetadataResponse
import com.example.protfolio.model.PhilosophyResponse
import com.example.protfolio.model.PlaygroundItemResponse
import com.example.protfolio.model.PortfolioResponse
import com.example.protfolio.model.ProfileResponse
import com.example.protfolio.model.SocialLinkResponse
import com.example.protfolio.model.TechnicalSkillResponse

fun PortfolioData.toResponse(): PortfolioResponse =
    PortfolioResponse(
        id = id.toHexString(),
        profile = profile.toResponse(),
        featuredWork = featuredWork.map { it.toResponse() },
        expertise = expertise.toResponse(),
        playground = playground.map { it.toResponse() },
        philosophy = philosophy.toResponse(),
        connect = connect.toResponse(),
        metadata = metadata.toResponse()
    )

fun Profile.toResponse() = ProfileResponse(
    name = name,
    title = title,
    profileImage = profileImage,
    resumeUrl = resumeUrl,
    tagline = tagline,
    summary = summary,
    availability = availability,
    contact = contact.toResponse()
)

fun Contact.toResponse() = ContactResponse(
    email = email,
    phone = phone
)

fun FeaturedWork.toResponse() = FeaturedWorkResponse(
    projectName = projectName,
    sector = sector,
    role = role,
    image = image,
    impactMetrics = impactMetrics.map { it.toResponse() },
    description = description,
    tags = tags
)

fun ImpactMetric.toResponse() = ImpactMetricResponse(
    label = label,
    value = value
)

fun Expertise.toResponse() = ExpertiseResponse(
    technical = technical.map { it.toResponse() },
    domain = domain
)

fun TechnicalSkill.toResponse() =
    TechnicalSkillResponse(
        skill = skill,
        rating = rating
    )

fun PlaygroundItem.toResponse() =
    PlaygroundItemResponse(
        title = title,
        image = image,
        description = description,
        link = link
    )

fun Philosophy.toResponse() = PhilosophyResponse(
    title = title,
    content = content
)

fun Connect.toResponse() = ConnectResponse(
    title = title,
    socials = socials.map { it.toResponse() }
)

fun SocialLink.toResponse() = SocialLinkResponse(
    platform = platform,
    url = url,
    icon = icon
)

fun MetadataTime.toResponse() = MetadataResponse(
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString(),
    version = version
)
