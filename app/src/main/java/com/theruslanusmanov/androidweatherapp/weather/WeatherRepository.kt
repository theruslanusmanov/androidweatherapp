package com.theruslanusmanov.androidweatherapp.weather

import com.theruslanusmanov.androidweatherapp.Config.FORECAST_HOST
import com.theruslanusmanov.androidweatherapp.common.BaseApiResponse
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ActivityRetainedScoped
class WeatherRepository @Inject constructor(
    private val forecastApiService: ForecastApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getForecast(lat: Float, lng: Float): NetworkResult<Forecast> {
        return withContext(defaultDispatcher) { safeApiCall { forecastApiService.getForecast("$FORECAST_HOST/v1/forecast?latitude=$lat&longitude=$lng&current=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min&timeformat=unixtime&timezone=Europe%2FMoscow&forecast_days=10&format=json") } }
    }
}