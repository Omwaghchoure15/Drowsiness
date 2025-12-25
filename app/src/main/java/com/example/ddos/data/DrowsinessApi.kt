package com.example.ddos.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class DrowsinessRequest(
    @SerializedName("imageBase64")
    val image: String
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
