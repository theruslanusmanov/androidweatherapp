package com.theruslanusmanov.androidweatherapp.network

import com.theruslanusmanov.androidweatherapp.domain.models.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val HOST = "https://api.open-meteo.com/v1"

interface ForecastApi {

    @GET("$HOST/forecast/")
    suspend fun getForecast(
        @Query("latitude") latitude: String = "55.7887",
        @Query("longitude") longitude: String = "49.1221",
        @Query("current") current: String = "temperature_2m,weathercode",
        @Query("daily") daily: String = "weathercode,temperature_2m_max,temperature_2m_min",
        @Query("timeformat") timeFormat: String = "unixtime",
        @Query("timezone") timezone: String = "Europe/Moscow",
        @Query("forecast_days") forecastDays: String = "10",
        @Query("format") format: String = "json",
    ): Response<Forecast>
}