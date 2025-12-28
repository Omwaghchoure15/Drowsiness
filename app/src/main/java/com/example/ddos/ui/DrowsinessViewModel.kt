package com.example.ddos.ui

import android.graphics.Bitmap
<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddos.data.ApiClient
import com.example.ddos.data.DrowsinessRequest
import com.example.ddos.util.toBase64
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
<<<<<<< HEAD
import androidx.core.graphics.scale
=======
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a

class DrowsinessViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var isSending = false
    private var lastSentTime = 0L

    private var drowsyCount = 0
    private var awakeCount = 0

<<<<<<< HEAD
    private val DROWSY_FRAMES = 3

    fun sendFrame(bitmap: Bitmap) {
        val now = System.currentTimeMillis()
        if (isSending || now - lastSentTime < 2000L) return
=======
    private val DROWSY_FRAMES = 3 // ~2–3 seconds

    fun sendFrame(bitmap: Bitmap) {
        val now = System.currentTimeMillis()
        if (isSending || now - lastSentTime < 800L) return
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a

        lastSentTime = now
        isSending = true

        viewModelScope.launch {
            try {
<<<<<<< HEAD
                val resized = bitmap.scale(224, 224)
=======
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
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

                        val finalDrowsy = drowsyCount >= DROWSY_FRAMES

                        _uiState.update {
                            it.copy(
                                isDrowsy = finalDrowsy,
                                ear = body.ear ?: 0f ,
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
<<<<<<< HEAD
                Log.e("API_ERROR", e.toString(), e)

                _uiState.update {
                    it.copy(
                        status = "Error",
                        error = e.localizedMessage ?: e.toString()
                    )
                }
            }
            finally {
                isSending = false
            }

=======
                _uiState.update {
                    it.copy(
                        status = "Network error",
                        error = e.message ?: "Unknown error"
                    )
                }
            } finally {
                isSending = false
            }
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
        }
    }
}