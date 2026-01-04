package com.theruslanusmanov.androidweatherapp.network

import com.theruslanusmanov.androidweatherapp.Config
import com.theruslanusmanov.androidweatherapp.search.Geocode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val HOST = "https://geocoding-api.open-meteo.com/v1"

interface GeocodeApi {

    @GET("$HOST/search/")
    suspend fun getGeocode(
        @Query("name") name: String,
        @Query("count") count: String = "10",
        @Query("language") language: String = "en",
        @Query("format") format: String = "json",
    ): Response<Geocode>
}