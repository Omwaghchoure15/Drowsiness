package com.example.ddos.data

<<<<<<< HEAD
import com.google.gson.annotations.SerializedName
=======
>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class DrowsinessRequest(
<<<<<<< HEAD
    @SerializedName("imageBase64")
    val image: String
)
=======
    val imageBase64: String
)

>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
data class DrowsinessResponse(
    val drowsy: Boolean,
    val ear: Float?
)
<<<<<<< HEAD
interface DrowsinessApi {
=======

interface DrowsinessApi {

>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
    @POST("predict")
    suspend fun sendFrame(
        @Body request: DrowsinessRequest
    ): Response<DrowsinessResponse>
<<<<<<< HEAD
=======

>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
}
