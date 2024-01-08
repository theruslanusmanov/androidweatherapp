package com.theruslanusmanov.androidweatherapp

import android.util.Log
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


object LocationScheme {
    val FIELD_CITY = stringPreferencesKey("city")
    val FIELD_LATITUDE = doublePreferencesKey("latitude")
    val FIELD_LONGITUDE = doublePreferencesKey("longitude")
}

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel(), LifecycleObserver {
    private val _currentLocation = MutableLiveData<String>()

    val currentLocation: LiveData<String>
        get() = _currentLocation

    init {
        getLocation()
    }

    private fun getLocation() = viewModelScope.launch {
        locationRepository.dataStore.edit { prefs ->
            prefs[LocationScheme.FIELD_CITY] = "Berlin"
            prefs[LocationScheme.FIELD_LATITUDE] = 52.520008
            prefs[LocationScheme.FIELD_LONGITUDE] = 13.404954
        }
        locationRepository.dataStore.data.collect() {
            _currentLocation.value = it.toString()
            Log.d("LOCATION_VIEWMODEL", it.toString())
        }
    }
}