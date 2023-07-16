package com.theruslanusmanov.androidweatherapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theruslanusmanov.androidweatherapp.City
import com.theruslanusmanov.androidweatherapp.LoadingState
import com.theruslanusmanov.androidweatherapp.Temperature
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.viewmodel.WeatherViewModel


val darkColor = Color(0xFF131759)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherApp(currentWeather: WeatherViewModel) {
    AndroidWeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = darkColor,
        ) {
            val currentConditions by currentWeather.uiCurrentConditionsState.observeAsState()
            val city by currentWeather.uiSearchCitiesState.observeAsState()

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
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
                                    modifier = Modifier.size(48.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.size(20.dp))
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

                Column(
                    Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                ) {
                    Text(text = "Weather now", fontWeight = FontWeight.Bold, color = darkColor)
                }
            }
        }
    }
}