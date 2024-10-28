package com.theruslanusmanov.androidweatherapp.weather

import com.theruslanusmanov.androidweatherapp.Config.FORECAST_HOST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ForecastApiService {

    @GET
    suspend fun getForecast(@Url url: String = "$FORECAST_HOST/v1/forecast?latitude=55.7887&longitude=49.1221&current=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min&timeformat=unixtime&timezone=Europe%2FMoscow&forecast_days=10&format=json"): Response<Forecast>
}