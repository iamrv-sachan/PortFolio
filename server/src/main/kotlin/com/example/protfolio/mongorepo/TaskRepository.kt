package com.example.protfolio.mongorepo

import PortfolioData

interface TaskRepository {
    suspend fun allTasks(): PortfolioData
}