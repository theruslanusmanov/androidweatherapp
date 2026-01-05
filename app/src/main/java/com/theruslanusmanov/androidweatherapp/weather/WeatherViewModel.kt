package com.theruslanusmanov.androidweatherapp.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import com.theruslanusmanov.androidweatherapp.data.LocationRepository
import com.theruslanusmanov.androidweatherapp.data.WeatherRepository
import com.theruslanusmanov.androidweatherapp.domain.models.Forecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CURRENT_LATITUDE = 55.7887F
const val CURRENT_LONGITUDE = 55.7887F

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _forecastState = MutableStateFlow<Forecast?>(null)
    val forecastState: StateFlow<Forecast?> = _forecastState.asStateFlow()

    init {
        getForecast(Pair(CURRENT_LATITUDE.toString(), CURRENT_LONGITUDE.toString()))
        viewModelScope.launch {
            locationRepository.getLocation().collect { location ->
                getForecast(location as Pair<String, String>)
            }
        }
    }

    private fun getForecast(location: Pair<String, String>) = viewModelScope.launch {
        _loading.value = true
        when (val result = weatherRepository.getForecast(location)) {
            is NetworkResult.Success -> {
                result.data?.let {
                    _forecastState.value = it
                }
            }

            else -> {
                Log.d("WEATHER_ERROR", result.message.toString())
            }
        }
        _loading.value = false
    }
}