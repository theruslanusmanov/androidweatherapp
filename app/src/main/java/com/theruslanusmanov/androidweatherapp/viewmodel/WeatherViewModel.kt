package com.theruslanusmanov.androidweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.bot.common.NetworkResult
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel(), LifecycleObserver {

    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String>
        get() = _uiState

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() = viewModelScope.launch {

        when (val result = weatherRepository.getCurrentWeather()) {
            is NetworkResult.Success -> {

                result.data?.let {
                    _uiState.value = it[0].Temperature.Metric.Value.toString()
                    Log.d("WEATHER_SUCCESS", it.toString())
                    Log.d("WEATHER_SUCCESS_VALUE", _uiState.value!!)
                }
            }
            else -> { Log.d("WEATHER_ERROR", result.message.toString())}
        }
    }
}