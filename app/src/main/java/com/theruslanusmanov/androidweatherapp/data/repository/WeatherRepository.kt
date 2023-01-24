package com.theruslanusmanov.androidweatherapp.data.repository

import com.theruslanusmanov.androidweatherapp.data.api.ApiService
import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModel
import com.theruslanusmanov.androidweatherapp.data.models.SearchCities
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import r.bot.common.BaseApiResponse
import r.bot.common.NetworkResult
import javax.inject.Inject

val API_KEY = "vxSytvG0mLbpKx8Lww4EDpXAfxTH1gNI";

@ActivityRetainedScoped
class WeatherRepository @Inject constructor(
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getCurrentWeather(): NetworkResult<CurrentConditionsModel> {
        return withContext(defaultDispatcher) { safeApiCall { apiService.getCurrentWeather(API_KEY) } }
    }

    suspend fun searchCities(): NetworkResult<SearchCities> {
        return withContext(defaultDispatcher) { safeApiCall { apiService.searchCities(API_KEY, "Ufa") } }
    }
}