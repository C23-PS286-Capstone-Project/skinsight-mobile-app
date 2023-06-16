package com.dayatmuhammad.skinsight.data

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    fun login(
        loginRequest: LoginRequest
    ): Flow<Result<LoginResponse>> =
        remoteDataSource.login(loginRequest)

    fun register(
        registerRequest: RegisterRequest
    ): Flow<Result<RegisterResponse>> =
        remoteDataSource.register(registerRequest)

    fun predict(
        token : String,
        image: MultipartBody.Part
    ): Flow<Result<PredictResponse>> =
        remoteDataSource.predict(token, image)

    fun getHistory(
        token : String,
    ): Flow<Result<HistoryResponse>> =
        remoteDataSource.getHistory(token)

    fun getProfile(
        token : String,
    ): Flow<Result<ProfileResponse>> =
        remoteDataSource.getProfile(token)
}