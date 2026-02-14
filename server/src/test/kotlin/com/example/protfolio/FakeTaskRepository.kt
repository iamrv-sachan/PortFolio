package com.example.protfolio

import com.example.protfolio.model.ContactItem
import com.example.protfolio.model.Contacts
import com.example.protfolio.model.Expertise
import com.example.protfolio.model.MetadataTime
import com.example.protfolio.model.PortfolioData
import com.example.protfolio.model.Profile
import com.example.protfolio.model.ProfileSummary
import com.example.protfolio.model.ProjectsGrid
import com.example.protfolio.mongorepo.TaskRepository
import java.time.Instant
import org.bson.types.ObjectId

class FakeTaskRepository : TaskRepository {

    override suspend fun allTasks(): PortfolioData {
        return PortfolioData(
            id = ObjectId(),
            profile = Profile(
                name = "Test User",
                title = "Android Developer",
                profileImage = "https://example.com/profile.png",
                resumeUrl = "https://example.com/resume.pdf",
                tagline = "Building cool things",
                highlightKeyword = "cool",
                summary = ProfileSummary(
                    primary = "Test summary primary",
                    secondary = "Test summary secondary"
                ),
                availability = "Available"
            ),
            contacts = Contacts(
                email = ContactItem("test@example.com", "email_icon"),
                phone = ContactItem("1234567890", "phone_icon"),
                github = ContactItem("github.com/test", "github_icon"),
                linkedin = ContactItem("linkedin.com/in/test", "linkedin_icon"),
                medium = ContactItem("medium.com/@test", "medium_icon")
            ),
            featuredWork = emptyList(),
            projectsGrid = ProjectsGrid(
                layout = "grid",
                projects = emptyList()
            ),
            expertise = Expertise(
                technical = emptyList()
            ),
            metadata = MetadataTime(
                updatedAt = Instant.now(),
                version = "1.0"
            )
        )
    }
}
