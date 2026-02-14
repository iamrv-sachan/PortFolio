package com.example.protfolio.mongorepo

import com.example.protfolio.model.PortfolioData

interface TaskRepository {
    suspend fun allTasks(): PortfolioData
}