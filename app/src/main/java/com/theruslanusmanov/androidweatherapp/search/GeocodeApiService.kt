package com.theruslanusmanov.androidweatherapp.search

import com.theruslanusmanov.androidweatherapp.Config.GEOCODE_HOST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodeApiService {

    @GET
    suspend fun getGeocode(@Url url: String = "$GEOCODE_HOST/v1/search?name=Berlin&count=10&language=en&format=json"): Response<Geocode>
}