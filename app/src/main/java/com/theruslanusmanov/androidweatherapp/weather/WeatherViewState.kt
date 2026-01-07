package com.theruslanusmanov.androidweatherapp.weather

import com.theruslanusmanov.androidweatherapp.domain.models.Forecast

sealed interface WeatherViewState {
    data object Loading : WeatherViewState
    data class Success(val data: Forecast) : WeatherViewState
}