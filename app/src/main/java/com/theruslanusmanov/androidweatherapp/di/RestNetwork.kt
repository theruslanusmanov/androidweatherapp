package com.theruslanusmanov.androidweatherapp.di

import com.theruslanusmanov.androidweatherapp.Config.FORECAST_HOST
import com.theruslanusmanov.androidweatherapp.network.ForecastApi
import com.theruslanusmanov.androidweatherapp.network.GeocodeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestNetwork {

    @Singleton
    @Provides
    fun provideBaseForecastURL(): String {
        return FORECAST_HOST
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build();
    }


    @Singleton
    @Provides
    fun provideRestAdapter(
        baseURL: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retro = Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()
                )
            ).build();
        return retro;
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ForecastApi {
        return retrofit.create(ForecastApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGeocodeApiService(retrofit: Retrofit): GeocodeApi {
        return retrofit.create(GeocodeApi::class.java)
    }


    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}