package com.example.protfolio

import com.example.protfolio.mapper.toResponse
import com.example.protfolio.mongorepo.MongoTaskRepository
import com.example.protfolio.mongorepo.TaskRepository
import com.example.protfolio.util.PortfolioUtil.getMongoUri
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: SERVER_PORT

    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        val repo = MongoTaskRepository(getMongoUri())
        configureSerialization(repo)
    }.start(wait = true)
}

fun Application.configureSerialization(repository: TaskRepository) {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
    }

    install(ContentNegotiation){
        json(
            json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
        )
    }
    routing {
        route("/") {
            get {
                call.respond("Server in Running...")
            }
        }
        route("/portfolio") {
            get {
                val tasks = repository.allTasks()
                call.respond(tasks.toResponse())
            }
        }
    }
}