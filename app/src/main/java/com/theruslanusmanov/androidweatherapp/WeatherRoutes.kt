package com.theruslanusmanov.androidweatherapp

import kotlinx.serialization.Serializable

@Serializable
sealed class WeatherRoutes {

    @Serializable
    object Main

    @Serializable
    object Search
}