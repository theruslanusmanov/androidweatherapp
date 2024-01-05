package com.theruslanusmanov.androidweatherapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class LocationRepository @Inject constructor(
    val dataStore: DataStore<Preferences>
)