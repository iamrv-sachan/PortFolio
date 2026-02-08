import java.time.Instant
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class PortfolioData(
    @BsonProperty("_id") val id: ObjectId,
    val profile: Profile,
    @BsonProperty("featured_work")
    val featuredWork: List<FeaturedWork>,
    val expertise: Expertise,
    val playground: List<PlaygroundItem>,
    val philosophy: Philosophy,
    val connect: Connect,
    val metadata: MetadataTime
)


data class Profile(
    val name: String,
    val title: String,
    @BsonProperty("profile_image")
    val profileImage: String,
    @BsonProperty("resume_url")
    val resumeUrl: String,
    val tagline: String,
    val summary: String,
    val availability: String,
    val contact: Contact
)

data class Contact(
    val email: String,
    val phone: String
)

data class FeaturedWork(
    @BsonProperty("project_name")
    val projectName: String,
    val sector: String,
    val role: String,
    val image: String,
    @BsonProperty("impact_metrics")
    val impactMetrics: List<ImpactMetric>,
    val description: String,
    val tags: List<String>
)

data class ImpactMetric(
    val label: String,
    val value: String
)

data class Expertise(
    val technical: List<TechnicalSkill>,
    val domain: List<String>
)

data class TechnicalSkill(
    val skill: String,
    val rating: Double
)

data class PlaygroundItem(
    val title: String,
    val image: String,
    val description: String,
    val link: String
)

data class Philosophy(
    val title: String,
    val content: String
)

data class Connect(
    val title: String,
    val socials: List<SocialLink>
)

data class SocialLink(
    val platform: String,
    val url: String,
    val icon: String
)

data class MetadataTime(
    val createdAt: Instant,
    val updatedAt: Instant,
    val version: String
)