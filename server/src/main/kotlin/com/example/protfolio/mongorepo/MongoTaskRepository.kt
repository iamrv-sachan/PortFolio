package com.example.protfolio.mongorepo

import PortfolioData
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

class MongoTaskRepository(connectionString: String) : TaskRepository {
    private val client: MongoClient

    private val database: MongoDatabase
    init {
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(
                PojoCodecProvider.builder().automatic(true).build()
            )
        )

        val settings: MongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .codecRegistry(codecRegistry)
            .build()

        client = MongoClient.create(settings)
        database = client.getDatabase("sample_mflix")
    }

    private val collection = database.getCollection<PortfolioData>("rajeev")

    override suspend fun allTasks(): PortfolioData {
        return collection.find().first()
    }
}