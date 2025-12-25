package com.example.ddos.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class DrowsinessRequest(
    val imageBase64: String
)

data class DrowsinessResponse(
    val drowsy: Boolean,
    val ear: Float?
)

interface DrowsinessApi {

    @POST("predict")
    suspend fun sendFrame(
        @Body request: DrowsinessRequest
    ): Response<DrowsinessResponse>

}
