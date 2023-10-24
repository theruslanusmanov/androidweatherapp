package com.theruslanusmanov.androidweatherapp.main


// API https://api.open-meteo.com/v1/forecast?latitude=55.7887&longitude=49.1221&hourly=temperature_2m,apparent_temperature,precipitation_probability,precipitation&timezone=Europe%2FMoscow&forecast_days=14
data class Forecast(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val HourlyUnits: HourlyUnits,
    val Hourly: Hourly,
)


class Hourly {
    var time: ArrayList<Int>? = null
    var temperature_2m: ArrayList<Double>? = null
    var apparent_temperature: ArrayList<Double>? = null
    var precipitation_probability: ArrayList<Int>? = null
    var precipitation: ArrayList<Double>? = null
}


class HourlyUnits {
    var time: String? = null
    var temperature_2m: String? = null
    var apparent_temperature: String? = null
    var precipitation_probability: String? = null
    var precipitation: String? = null
}