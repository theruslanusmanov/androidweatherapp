package com.theruslanusmanov.androidweatherapp

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _currentLocation = MutableStateFlow<Preferences?>(null)

    val currentLocation: StateFlow<Preferences?>
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

        locationRepository.dataStore.data.collect {
            _currentLocation.emit(it)
        }
    }
}