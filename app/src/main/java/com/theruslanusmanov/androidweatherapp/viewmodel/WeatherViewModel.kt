package com.theruslanusmanov.androidweatherapp.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.data.models.CurrentConditionsModel
import com.theruslanusmanov.androidweatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.bot.common.NetworkResult
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel(), LifecycleObserver {

    init {
        getCurrentWeather()
    }

    val snapshotStateList = SnapshotStateList<CurrentConditionsModel>()

    private fun getCurrentWeather() = viewModelScope.launch {
        when (val result = weatherRepository.getCurrentWeather()) {
            is NetworkResult.Success -> {
                result.data?.let { snapshotStateList.addAll(it) }
            }
            else -> {}
        }
    }
}