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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val weatherRepository: WeatherRepository, @ApplicationContext private val application: Context) :
    ViewModel(), LifecycleObserver {
    private val _forecastState = MutableLiveData<Forecast>()
    val forecastState: LiveData<Forecast>
        get() = _forecastState

    init {
        getForecast()
    }

    private fun getForecast() = viewModelScope.launch {
        val pref = application.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        when (val result = weatherRepository.getForecast(pref.getFloat("latitude", 55.7887F), pref.getFloat("longitude", 49.1221F))) {
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