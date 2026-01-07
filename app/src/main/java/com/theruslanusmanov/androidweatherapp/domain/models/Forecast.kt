package com.theruslanusmanov.androidweatherapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("generationtime_ms") val generationtimeMs: Double? = null,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds: Int? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("timezone_abbreviation") val timezoneAbbreviation: String? = null,
    @SerialName("elevation") val elevation: Int? = null,
    @SerialName("current_units") val currentUnits: CurrentUnits? = null,
    @SerialName("current") val current: Current? = null,
    @SerialName("daily_units") val dailyUnits: DailyUnits? = null,
    @SerialName("daily") val daily: Daily? = null
)

@Serializable
data class Current(
    @SerialName("time") val time: Int? = null,
    @SerialName("interval") val interval: Int? = null,
    @SerialName("temperature_2m") val temperature2m: Double? = 0.0,
    @SerialName("weathercode") val weathercode: Int? = null
)

@Serializable
data class CurrentUnits(
    @SerialName("time") val time: String? = null,
    @SerialName("interval") val interval: String? = null,
    @SerialName("temperature_2m") val temperature2m: String? = null,
    @SerialName("weathercode") val weathercode: String? = null
)

@Serializable
data class Daily(
    @SerialName("time") val time: List<Int> = listOf(),
    @SerialName("weathercode") val weathercode: List<Int> = listOf(),
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double> = listOf(),
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double> = listOf()
)

@Serializable
data class DailyUnits(
    @SerialName("time") val time: String? = null,
    @SerialName("weathercode") val weathercode: String? = null,
    @SerialName("temperature_2m_max") val temperature2mMax: String? = null,
    @SerialName("temperature_2m_min") val temperature2mMin: String? = null
)
