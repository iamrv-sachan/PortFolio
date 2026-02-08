package com.example.protfolio

import Connect
import Contact
import Expertise
import MetadataTime
import Philosophy
import PortfolioData
import Profile
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
                summary = "Test summary",
                availability = "Available",
                contact = Contact(
                    email = "test@example.com",
                    phone = "1234567890"
                )
            ),
            featuredWork = emptyList(),
            expertise = Expertise(
                technical = emptyList(),
                domain = emptyList()
            ),
            playground = emptyList(),
            philosophy = Philosophy(
                title = "Test Philosophy",
                content = "Write clean code"
            ),
            connect = Connect(
                title = "Connect",
                socials = emptyList()
            ),
            metadata = MetadataTime(
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                version = "1.0"
            )
        )
    }
}
