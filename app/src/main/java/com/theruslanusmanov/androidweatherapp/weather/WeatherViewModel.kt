package com.theruslanusmanov.androidweatherapp.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import com.theruslanusmanov.androidweatherapp.data.repository.LocationRepository
import com.theruslanusmanov.androidweatherapp.data.repository.WeatherRepository
import com.theruslanusmanov.androidweatherapp.domain.models.Forecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_LATITUDE = 55.7887
const val DEFAULT_LONGITUDE = 55.7887
const val DEFAULT_LOCATION_NAME = "Kazan'"

interface WeatherViewModel {
    val loading: StateFlow<Boolean>
    val forecastState: StateFlow<Forecast>
    val locationName: StateFlow<String>
}

class FakeWeatherViewModel() : ViewModel(), WeatherViewModel {
    override val loading: StateFlow<Boolean> = MutableStateFlow(false)
    override val forecastState: StateFlow<Forecast> = MutableStateFlow(Forecast())
    override val locationName: StateFlow<String> = MutableStateFlow(DEFAULT_LOCATION_NAME)
}

@HiltViewModel
class DefaultWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : ViewModel(), WeatherViewModel {

    private val _loading = MutableStateFlow(false)
    override val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _forecastState = MutableStateFlow(Forecast())
    override val forecastState: StateFlow<Forecast> = _forecastState.asStateFlow()

    override val locationName: StateFlow<String> = locationRepository.getLocationName()
        .map {
            if (it.isNullOrEmpty()) DEFAULT_LOCATION_NAME else it
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DEFAULT_LOCATION_NAME,
        )

    init {
        getForecast(Pair(DEFAULT_LATITUDE.toString(), DEFAULT_LONGITUDE.toString()))
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