package com.dayatmuhammad.skinsight.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.dayatmuhammad.skinsight.api.ApiService
import com.dayatmuhammad.skinsight.data.RemoteDataSource
import com.dayatmuhammad.skinsight.data.Repository
import com.dayatmuhammad.skinsight.preference.SharedPreference
import com.dayatmuhammad.skinsight.preference.SharedPreferenceImpl
import com.dayatmuhammad.skinsight.utils.Constant
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsPreferenceDataSource(
        sharedPreferenceImpl: SharedPreferenceImpl
    ): SharedPreference

    companion object {

        @Provides
        @Singleton
        fun provideService(builder: Retrofit.Builder): ApiService {
            return builder
                .build()
                .create(ApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideMoshi(): Moshi {
            return Moshi.Builder().build()
        }

        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient {

            val okhttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS) // Read timeout
                .writeTimeout(30, TimeUnit.SECONDS) // Write timeout
                .addInterceptor(httpLoggingInterceptor)
            return okhttpClient.build()

        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl("https://skinsight-server-app-dmn2lplbqq-et.a.run.app/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
        }


        @Provides
        @Singleton
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences(
                Constant.SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        }

        @Singleton
        @Provides
        fun provideRemoteDataSource(apiservice: ApiService): RemoteDataSource =
            RemoteDataSource(apiservice)

        @Singleton
        @Provides
        fun provideRepository(remoteDataSource: RemoteDataSource) =
            Repository(remoteDataSource)
    }
}