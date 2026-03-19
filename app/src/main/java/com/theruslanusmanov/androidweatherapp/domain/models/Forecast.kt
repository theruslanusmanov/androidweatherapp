@file:OptIn(ExperimentalSerializationApi::class)

package com.theruslanusmanov.androidweatherapp.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class Forecast(
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("generationtime_ms") val generationTimeMs: Double? = null,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds: Int? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("timezone_abbreviation") val timezoneAbbreviation: String? = null,
    @SerialName("elevation") val elevation: Double? = null,
    @SerialName("current_units") val currentUnits: CurrentUnits? = null,
    @SerialName("current") val current: Current? = null,
    @SerialName("daily_units") val dailyUnits: DailyUnits? = null,
    @SerialName("daily") val daily: Daily? = null
)

@Serializable
@JsonIgnoreUnknownKeys
data class Current(
    @SerialName("time") val time: Int? = null,
    @SerialName("interval") val interval: Int? = null,
    @SerialName("temperature_2m") val temperature2m: Double? = null,
    @SerialName("weathercode") val weathercode: Int? = null
)

@Serializable
@JsonIgnoreUnknownKeys
data class CurrentUnits(
    @SerialName("time") val time: String? = null,
    @SerialName("interval") val interval: String? = null,
    @SerialName("temperature_2m") val temperature2m: String? = null,
    @SerialName("weathercode") val weathercode: String? = null
)

@Serializable
@JsonIgnoreUnknownKeys
data class Daily(
    @SerialName("time") val time: List<Int> = emptyList(),
    @SerialName("weathercode") val weathercode: List<Int> = emptyList(),
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double> = emptyList(),
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double> = emptyList(),
    @SerialName("wind_speed_10m_max") val windSpeed: List<Double> = emptyList(),
    @SerialName("wind_direction_10m_dominant") val windDirection: List<Int> = emptyList(),
    @SerialName("rain_sum") val rainSum: List<Double> = emptyList(),
)

@Serializable
@JsonIgnoreUnknownKeys
data class DailyUnits(
    @SerialName("time") val time: String? = null,
    @SerialName("weathercode") val weathercode: String? = null,
    @SerialName("temperature_2m_max") val temperature2mMax: String? = null,
    @SerialName("temperature_2m_min") val temperature2mMin: String? = null
)
