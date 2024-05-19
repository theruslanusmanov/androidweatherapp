package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theruslanusmanov.androidweatherapp.common.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel(), LifecycleObserver {

    val _searchQuery = mutableStateOf("")

    private val _searchState = MutableLiveData<Geocode>()
    val searchState: LiveData<Geocode>
        get() = _searchState

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun getSearch(query: String) = viewModelScope.launch {

        when (val result = searchRepository.getSearch(query)) {
            is NetworkResult.Success -> {

                result.data?.let {
                    _searchState.value = it
                    Log.d("WEATHER_SUCCESS", it.toString())
                }
            }

            else -> {
                Log.d("WEATHER_ERROR", result.message.toString())
            }
        }
    }
}