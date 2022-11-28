package com.theruslanusmanov.androidweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class CurrentConditionsModel(
    @field:SerializedName("Temperature.Metric.Value")
    val temperature: String,
)
