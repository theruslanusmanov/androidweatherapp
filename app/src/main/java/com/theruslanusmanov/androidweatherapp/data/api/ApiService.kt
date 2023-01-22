package com.theruslanusmanov.androidweatherapp.data.api

import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/currentconditions/v1/292177")
    suspend fun getCurrentWeather(@Query("apikey") apiKey: String): Response<CurrentConditionsModel>
}