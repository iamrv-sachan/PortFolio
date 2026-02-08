package com.example.protfolio

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testGetPortfolio() = testApplication {
        val fakeRepo = FakeTaskRepository()

        application {
            configureSerialization(fakeRepo)
        }

        val response = client.get("/tasks")

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().isNotBlank())
    }
}