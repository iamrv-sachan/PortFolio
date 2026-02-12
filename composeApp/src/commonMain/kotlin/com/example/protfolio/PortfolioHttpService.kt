package com.example.protfolio

import com.example.protfolio.model.PortfolioResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class PortfolioHttpService {

    private val client = getHttpClient()

    val scope = CoroutineScope(Dispatchers.Default)

    init {
        getPortFolioUiState()
    }

    private val _uiState: MutableStateFlow<PortfolioUiState> =
        MutableStateFlow(PortfolioUiState.Loading)
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()


    private fun getPortFolioUiState() {
        scope.launch {
            try {
                val response = getPortfolioApiResponse()
                _uiState.value = PortfolioUiState.SuccessData(response)
            } catch (e: Exception) {
                _uiState.value = PortfolioUiState.Error
            }
        }
    }

    private suspend fun getPortfolioApiResponse(): PortfolioResponse {
        return client.get("https://protfolio-production-7975.up.railway.app/portfolio").body()
    }

    private fun getHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }
}

sealed interface PortfolioUiState {
    data class SuccessData(val data: PortfolioResponse) : PortfolioUiState
    object Error : PortfolioUiState
    object Loading : PortfolioUiState
}