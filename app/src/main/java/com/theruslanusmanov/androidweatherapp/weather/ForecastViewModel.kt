package com.theruslanusmanov.androidweatherapp.weather

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.bot.common.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel(), LifecycleObserver {
    private val _forecastState = MutableLiveData<Forecast>()
    val forecastState: LiveData<Forecast>
        get() = _forecastState

    init {
        getForecast()
    }

    private fun getForecast() = viewModelScope.launch {

        when (val result = weatherRepository.getForecast()) {
            is NetworkResult.Success -> {

                result.data?.let {
                    _forecastState.value = it
                    Log.d("WEATHER_SUCCESS", it.toString())
                }
            }

            else -> {
                Log.d("WEATHER_ERROR", result.message.toString())
            }
        }
    }
}