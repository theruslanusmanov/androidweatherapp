package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
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

    private val _searchState = MutableLiveData<Geocode>()
    val searchState: LiveData<Geocode>
        get() = _searchState

    init {
        getSearch()
    }

    private fun getSearch() = viewModelScope.launch {

        when (val result = searchRepository.getSearch()) {
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