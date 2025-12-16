package com.theruslanusmanov.androidweatherapp.weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @param:ApplicationContext private val application: Context
) : ViewModel(), LifecycleObserver {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _forecastState = MutableStateFlow<Forecast?>(null)
    val forecastState: StateFlow<Forecast?> = _forecastState.asStateFlow()

    init {
        getForecast()
    }

    private fun getForecast() = viewModelScope.launch {
        _loading.value = true
        val pref = application.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        when (val result = weatherRepository.getForecast(
            pref.getFloat("latitude", CURRENT_LATITUDE),
            pref.getFloat("longitude", CURRENT_LONGITUDE)
        )) {
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
        _loading.value = false
    }
}