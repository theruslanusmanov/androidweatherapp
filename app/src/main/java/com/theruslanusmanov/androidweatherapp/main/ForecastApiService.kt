package com.theruslanusmanov.androidweatherapp.main

import com.theruslanusmanov.androidweatherapp.main.Forecast
import retrofit2.Response
import retrofit2.http.GET

interface ForecastApiService {

    @GET("/v1/forecast?latitude=55.7887&longitude=49.1221&hourly=temperature_2m,apparent_temperature,precipitation_probability,precipitation&timezone=Europe%2FMoscow&forecast_days=14")
    suspend fun getForecast(): Response<Forecast>
}