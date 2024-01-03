package com.theruslanusmanov.androidweatherapp.search

import com.theruslanusmanov.androidweatherapp.common.BaseApiResponse
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ActivityRetainedScoped
class SearchRepository @Inject constructor(
    private val geocodeApiService: GeocodeApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getSearch(): NetworkResult<Geocode> {
        return withContext(defaultDispatcher) { safeApiCall { geocodeApiService.getGeocode() } }
    }
}