package com.theruslanusmanov.androidweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModelItem
import com.theruslanusmanov.androidweatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.bot.common.NetworkResult
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel(), LifecycleObserver {

    private val _uiCurrentConditionsState = MutableLiveData<CurrentConditionsModelItem>()
    val uiCurrentConditionsState: LiveData<CurrentConditionsModelItem>
        get() = _uiCurrentConditionsState

    private val _uiSearchCitiesState = MutableLiveData<String>()
    val uiSearchCitiesState: LiveData<String>
        get() = _uiSearchCitiesState

    init {
        getCurrentWeather()
        getCity()
    }

    private fun getCurrentWeather() = viewModelScope.launch {

        when (val result = weatherRepository.getCurrentWeather()) {
            is NetworkResult.Success -> {

                result.data?.let {
                    _uiCurrentConditionsState.value = it[0]
                    Log.d("WEATHER_SUCCESS", it.toString())
                }
            }
            else -> { Log.d("WEATHER_ERROR", result.message.toString())}
        }
    }

    private fun getCity() = viewModelScope.launch {

        when (val result = weatherRepository.searchCities()) {
            is NetworkResult.Success -> {

                result.data?.let {
                    _uiSearchCitiesState.value = it[0].EnglishName
                    Log.d("SEARCH_CITIES_SUCCESS", it.toString())
                }
            }
            else -> { Log.d("SEARCH_CITIES_ERROR", result.message.toString())}
        }
    }
}