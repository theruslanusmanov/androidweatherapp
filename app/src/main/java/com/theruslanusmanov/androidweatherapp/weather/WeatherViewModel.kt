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
    val uiState: StateFlow<WeatherViewState>
    val location: StateFlow<Pair<String, String>>
    val locationName: StateFlow<String>

    fun refresh()
}

class FakeWeatherViewModel() : ViewModel(), WeatherViewModel {
    override val uiState: StateFlow<WeatherViewState> = MutableStateFlow(
        WeatherViewState.Success(Forecast())
    )
    override val location: StateFlow<Pair<String, String>> =
        MutableStateFlow(Pair(DEFAULT_LATITUDE.toString(), DEFAULT_LONGITUDE.toString()))
    override val locationName: StateFlow<String> = MutableStateFlow(DEFAULT_LOCATION_NAME)

    override fun refresh() {}
}

@HiltViewModel
class DefaultWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : ViewModel(), WeatherViewModel {

    private val _uiState = MutableStateFlow<WeatherViewState>(WeatherViewState.Loading)
    override val uiState: StateFlow<WeatherViewState> = _uiState.asStateFlow()

    private val _location =
        MutableStateFlow(Pair(DEFAULT_LATITUDE.toString(), DEFAULT_LONGITUDE.toString()))
    override val location: StateFlow<Pair<String, String>> = _location.asStateFlow()

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
        getForecast(location.value)
        viewModelScope.launch {
            locationRepository.getLocation().collect { location ->
                location?.let {
                    _location.value = location as Pair<String, String>
                    getForecast(location as Pair<String, String>)
                }
            }
        }
    }

    override fun refresh() {
        getForecast(location.value)
    }

    private fun getForecast(location: Pair<String, String>) = viewModelScope.launch {
        when (val result = weatherRepository.getForecast(location)) {
            is NetworkResult.Success -> {
                result.data?.let {
                    _uiState.value = WeatherViewState.Success(it)
                }
            }

            else -> {
                Log.d("WEATHER_ERROR", result.message.toString())
            }
        }
    }
}