package com.theruslanusmanov.androidweatherapp

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


object UserScheme {
    val FIELD_NAME = stringPreferencesKey("name")
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
            prefs[UserScheme.FIELD_NAME] = "John"
        }
        locationRepository.dataStore.data.collect() {
            _currentLocation.value = it.toString()
            Log.d("LOCATION_VIEWMODEL", it.toString())
        }
    }
}