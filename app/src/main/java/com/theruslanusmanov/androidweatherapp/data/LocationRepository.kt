package com.theruslanusmanov.androidweatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val GEO_PREFERENCES_NAME = "geo_preferences"

val Context.geoPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = GEO_PREFERENCES_NAME
)

val GEO_LATITUDE_KEY: Preferences.Key<String> = stringPreferencesKey("latitude")
val GEO_LONGITUDE_KEY: Preferences.Key<String> = stringPreferencesKey("longitude")

@ActivityRetainedScoped
class LocationRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    suspend fun saveLocation(location: Pair<String, String>) {
        context.geoPreferencesDataStore.edit { settings ->
            settings[GEO_LATITUDE_KEY] = location.first
            settings[GEO_LONGITUDE_KEY] = location.second
        }
    }

    fun getLocation(): Flow<Pair<String?, String?>?> = flow {
        context.geoPreferencesDataStore.data
            .map { Pair(it[GEO_LATITUDE_KEY], it[GEO_LONGITUDE_KEY]) }
            .collect { emit(it) }
    }
}