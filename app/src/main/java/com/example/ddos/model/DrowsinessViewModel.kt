package com.example.ddos.model

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddos.api.ApiClient
import com.example.ddos.api.DrowsinessRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.core.graphics.scale

class DrowsinessViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState
    private var isSending = false
    private var lastSentTime = 0L
    private var drowsyCount = 0
    private var awakeCount = 0

    private val DrowsyFrames = 3 // ~2–3 seconds

    fun sendFrame(bitmap: Bitmap) {
        val now = System.currentTimeMillis()
        if (isSending || now - lastSentTime < 2000L) return

        fun sendFrame(bitmap: Bitmap) {
            val now = System.currentTimeMillis()
            if (isSending || now - lastSentTime < 800L) return

            lastSentTime = now
            isSending = true

            viewModelScope.launch {
                try {
                val resized = bitmap.scale(224, 224)
                val base64 = bitmap.toBase64()

                val response = ApiClient.api.sendFrame(
                    DrowsinessRequest(base64)
                )

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (body.drowsy) {
                            drowsyCount++
                            awakeCount = 0
                        } else {
                            awakeCount++
                            drowsyCount = 0
                        }

                        val finalDrowsy = drowsyCount >= DrowsyFrames
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
                } finally { isSending = false }
            }
        }
    }
}