package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import com.theruslanusmanov.androidweatherapp.data.LocationRepository
import com.theruslanusmanov.androidweatherapp.data.SearchRepository
import com.theruslanusmanov.androidweatherapp.domain.models.Geocode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    val searchState: StateFlow<Geocode?> = searchQuery
        .debounce(1000L)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .map { query ->
            when (val result = searchRepository.getSearch(query)) {
                is NetworkResult.Success -> {
                    result.data
                }

                else -> {
                    Log.d("WEATHER_ERROR", result.message.toString())
                    null
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun saveLocation(location: Pair<String, String>) {
        viewModelScope.launch {
            locationRepository.saveLocation(location)
        }
    }

    fun saveLocationName(name: String) {
        viewModelScope.launch {
            locationRepository.saveLocationName(name)
        }
    }
}