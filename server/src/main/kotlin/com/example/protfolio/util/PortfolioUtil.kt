package com.example.protfolio.util

import java.io.File
import java.io.FileInputStream
import java.util.Properties

object PortfolioUtil {
    fun getMongoUri(): String {
        val properties = Properties()
        val localPropertiesFile = File("local.properties")
        val mongoUri = if (localPropertiesFile.exists()) {
            FileInputStream(localPropertiesFile).use { properties.load(it) }
            properties.getProperty("mongo.uri")
        } else {
            System.getenv("MONGO_URI")
        }

        if (mongoUri.isNullOrBlank()) {
            error("mongo.uri not found in local.properties or MONGO_URI environment variable is not set.")
        }
        return mongoUri
    }
}