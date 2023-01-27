package com.theruslanusmanov.androidweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherApp(currentWeather: WeatherViewModel) {
    AndroidWeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF131759),
        ) {
            val currentConditions by currentWeather.uiCurrentConditionsState.observeAsState()
            val city by currentWeather.uiSearchCitiesState.observeAsState()

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                if (currentConditions == null) {
                    LoadingState()
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        city?.let { City(name = it) }
                        Text("Today, Jan 1, 23:50", style = TextStyle(color = Color.White))
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Column() {
                            Icon(
                                Icons.Rounded.Place,
                                contentDescription = "Weather icon",
                            )
                            currentConditions?.let {
                                Text(
                                    it.WeatherText,
                                    style = TextStyle(color = Color.White)
                                )
                            }
                        }
                        currentConditions?.let { Temperature(it.Temperature.Metric.Value.toString()) }
                    }
                }
            }
        }
    }
}
