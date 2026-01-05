package com.theruslanusmanov.androidweatherapp.weather

import com.theruslanusmanov.androidweatherapp.common.BaseApiResponse
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import com.theruslanusmanov.androidweatherapp.domain.models.Forecast
import com.theruslanusmanov.androidweatherapp.network.ForecastApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ActivityRetainedScoped
class WeatherRepository @Inject constructor(
    private val forecastApi: ForecastApi,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getForecast(lat: Float, lng: Float): NetworkResult<Forecast> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                forecastApi.getForecast(latitude = "$lat", longitude = "$lng")
            }
        }
    }
}