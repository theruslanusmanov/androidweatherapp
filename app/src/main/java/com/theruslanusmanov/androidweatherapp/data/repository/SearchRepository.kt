package com.theruslanusmanov.androidweatherapp.data.repository

import com.theruslanusmanov.androidweatherapp.common.BaseApiResponse
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import com.theruslanusmanov.androidweatherapp.domain.models.Geocode
import com.theruslanusmanov.androidweatherapp.network.GeocodeApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SearchRepository @Inject constructor(
    private val geocodeApi: GeocodeApi,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getSearch(city: String): NetworkResult<Geocode> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                geocodeApi.getGeocode(name = city)
            }
        }
    }
}