package com.example.protfolio

import com.example.protfolio.mapper.toResponse
import com.example.protfolio.model.PortfolioResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import protfolio.composeapp.generated.resources.Res

class PortfolioHttpService {

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
                _uiState.value = PortfolioUiState.Error(e.message)
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun getPortfolioApiResponse(): PortfolioResponse {
        val bytes = Res.readBytes("files/portfolio.json")
        val jsonString = bytes.decodeToString()
        val jsonParser = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
        val portfolioData = jsonParser.decodeFromString<com.example.protfolio.model.PortfolioData>(jsonString)
        return portfolioData.toResponse()
    }
}

sealed interface PortfolioUiState {
    data class SuccessData(val data: PortfolioResponse) : PortfolioUiState
    data class Error(val error: String? = null) : PortfolioUiState
    object Loading : PortfolioUiState
}