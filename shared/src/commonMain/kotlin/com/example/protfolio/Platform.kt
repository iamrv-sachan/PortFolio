package com.example.protfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform