package com.theruslanusmanov.androidweatherapp.weather

import retrofit2.Response
import retrofit2.http.GET

interface ForecastApiService {

    @GET("/v1/forecast?latitude=55.7887&longitude=49.1221&current=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min&timeformat=unixtime&timezone=Europe%2FMoscow&forecast_days=10&format=json")
    suspend fun getForecast(): Response<Forecast>
}