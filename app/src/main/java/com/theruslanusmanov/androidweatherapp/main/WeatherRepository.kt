package com.theruslanusmanov.androidweatherapp.main

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import r.bot.common.BaseApiResponse
import r.bot.common.NetworkResult
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