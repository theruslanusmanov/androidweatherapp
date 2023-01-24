package com.theruslanusmanov.androidweatherapp.data.api

import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModel
import com.theruslanusmanov.androidweatherapp.data.models.SearchCities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/currentconditions/v1/292177")
    suspend fun getCurrentWeather(@Query("apikey") apiKey: String): Response<CurrentConditionsModel>

    @GET("/locations/v1/cities/search")
    suspend fun searchCities(@Query("apikey") apiKey: String, @Query("q") query: String): Response<SearchCities>
}