package com.example.ddos.model

import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddos.api.ApiClient
import com.example.ddos.api.DrowsinessRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrowsinessViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var isSending = false
    private var lastSentTime = 0L
    private var drowsyCount = 0
    private var awakeCount = 0
    private var drowsyScore = 0

    private val DROWSY_FRAMES = 1
    private val SEND_INTERVAL_MS = 2000L

    fun sendFrame(bitmap: Bitmap) {
        val now = System.currentTimeMillis()
        if (isSending || now - lastSentTime < SEND_INTERVAL_MS) return

        lastSentTime = now
        isSending = true

        viewModelScope.launch {
            try {

                val resized = bitmap.scale(224, 224)
                val base64 = resized.toBase64()

                val response = ApiClient.api.sendFrame(
                    DrowsinessRequest(base64)
                )

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (body.drowsy) {
                            drowsyScore += 2
                        } else {
                            drowsyScore -= 1
                        }

                        drowsyScore = drowsyScore.coerceIn(0, 5)

                        val finalDrowsy = drowsyScore >= 3

                        _uiState.update {
                            it.copy(
                                isDrowsy = finalDrowsy,
                                ear = body.ear ?: 0f,
                                status = if (finalDrowsy) "DROWSY!" else "Awake",
                                error = ""
                            )
                        }
                    }
                } else {
                    val err = response.errorBody()?.string()
                    Log.e("API_SERVER", "HTTP ${response.code()} → $err")

                    _uiState.update {
                        it.copy(
                            status = "Server error",
                            error = "HTTP ${response.code()}"
                        )
                    }
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", e.toString(), e)
                _uiState.update {
                    it.copy(
                        status = "Error",
                        error = e.localizedMessage ?: e.toString()
                    )
                }
            } finally {
                isSending = false
            }
        }
    }
}
