package com.theruslanusmanov.androidweatherapp

import androidx.datastore.core.DataStore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.prefs.Preferences
import javax.inject.Inject


@ActivityRetainedScoped
class LocationRepository @Inject constructor(
//    val dataStore: DataStore<Preferences>
)