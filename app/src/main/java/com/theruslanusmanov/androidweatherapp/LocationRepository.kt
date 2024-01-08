package com.theruslanusmanov.androidweatherapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class LocationRepository @Inject constructor(
    val dataStore: DataStore<Preferences>
)