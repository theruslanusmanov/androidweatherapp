package com.theruslanusmanov.androidweatherapp.data.api

import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/currentconditions/v1/1")
    suspend fun getCurrentWeather(): Response<List<CurrentConditionsModel>>
}