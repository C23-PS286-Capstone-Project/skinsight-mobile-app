package com.dayatmuhammad.skinsight.api

import com.dayatmuhammad.skinsight.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginResponse


    @POST("api/v1/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @Multipart
    @POST("api/v1/faces/predict")
    suspend fun predict(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ) : PredictResponse


    @GET("api/v1/histories")
    suspend fun getHistory(
        @Header("Authorization") token: String,
    ) : HistoryResponse

    @GET("api/v1/users/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ) : ProfileResponse
}