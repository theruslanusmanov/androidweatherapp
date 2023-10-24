package com.theruslanusmanov.androidweatherapp.weather

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.theruslanusmanov.androidweatherapp.common.BaseApiResponse
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import javax.inject.Inject


@ActivityRetainedScoped
class WeatherRepository @Inject constructor(
    private val forecastApiService: ForecastApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getForecast(): NetworkResult<Forecast> {
        return withContext(defaultDispatcher) { safeApiCall { forecastApiService.getForecast() } }
    }
}