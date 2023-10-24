package com.theruslanusmanov.androidweatherapp.weather

import com.google.gson.annotations.SerializedName


// API https://api.open-meteo.com/v1/forecast?latitude=55.7887&longitude=49.1221&current=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min&timeformat=unixtime&timezone=Europe%2FMoscow&forecast_days=10&format=json
data class Forecast(
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("generationtime_ms") var generationtimeMs: Double,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int,
    @SerializedName("timezone") var timezone: String,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String,
    @SerializedName("elevation") var elevation: Int,
    @SerializedName("current_units") var currentUnits: CurrentUnits,
    @SerializedName("current") var current: Current,
    @SerializedName("daily_units") var dailyUnits: DailyUnits,
    @SerializedName("daily") var daily: Daily
)

data class Current(
    @SerializedName("time") var time: Int,
    @SerializedName("interval") var interval: Int,
    @SerializedName("temperature_2m") var temperature2m: Double,
    @SerializedName("weathercode") var weathercode: Int
)

data class CurrentUnits(
    @SerializedName("time") var time: String,
    @SerializedName("interval") var interval: String,
    @SerializedName("temperature_2m") var temperature2m: String,
    @SerializedName("weathercode") var weathercode: String
)

data class Daily(
    @SerializedName("time") var time: ArrayList<Int> = arrayListOf(),
    @SerializedName("weathercode") var weathercode: ArrayList<Int> = arrayListOf(),
    @SerializedName("temperature_2m_max") var temperature2mMax: ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min") var temperature2mMin: ArrayList<Double> = arrayListOf()
)

data class DailyUnits(
    @SerializedName("time") var time: String,
    @SerializedName("weathercode") var weathercode: String,
    @SerializedName("temperature_2m_max") var temperature2mMax: String,
    @SerializedName("temperature_2m_min") var temperature2mMin: String
)