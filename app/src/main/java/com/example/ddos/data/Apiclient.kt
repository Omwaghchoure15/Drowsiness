package com.example.ddos.data

<<<<<<< HEAD
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: DrowsinessApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://drowsiness-api.onrender.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DrowsinessApi::class.java)
=======
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = ""


    val api: DrowsinessApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DrowsinessApi::class.java)

>>>>>>> 8d198eecdf3bf42f79ee7773e982fd9283528a2a
    }
}
