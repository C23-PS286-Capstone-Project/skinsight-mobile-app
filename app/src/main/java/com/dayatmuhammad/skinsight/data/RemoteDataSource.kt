package com.dayatmuhammad.skinsight.data

import com.dayatmuhammad.skinsight.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    fun login(
        loginRequest: LoginRequest
    ): Flow<Result<LoginResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.login(loginRequest)
                emit(Result.Success(response))
            } catch (t: HttpException) {
                emit(Result.Error(true, t.code(), t.response()?.errorBody()))
            }
        }

    fun register(
        registerRequest: RegisterRequest
    ): Flow<Result<RegisterResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.register(registerRequest)
                emit(Result.Success(response))
            } catch (t: HttpException) {
                emit(Result.Error(true, t.code(), t.response()?.errorBody()))
            }
        }

    fun getHistory(
        token: String
    ): Flow<Result<HistoryResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.getHistory(token)
                emit(Result.Success(response))
            } catch (t: HttpException) {
                emit(Result.Error(true, t.code(), t.response()?.errorBody()))
            }
        }

    fun getProfile(
        token: String
    ): Flow<Result<ProfileResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.getProfile(token)
                emit(Result.Success(response))
            } catch (t: HttpException) {
                emit(Result.Error(true, t.code(), t.response()?.errorBody()))
            }
        }

    fun predict(
        token : String,
        image: MultipartBody.Part
    ): Flow<Result<PredictResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.predict(token, image)
                emit(Result.Success(response))
            } catch (t: HttpException) {
                emit(Result.Error(true, t.code(), t.response()?.errorBody()))
            }
        }
}