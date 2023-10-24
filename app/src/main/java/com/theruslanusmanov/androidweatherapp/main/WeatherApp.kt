package com.theruslanusmanov.androidweatherapp.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.ui.theme.fontFamily
import com.theruslanusmanov.androidweatherapp.viewmodel.ForecastViewModel


@Composable
fun WeatherApp(forecastViewModel: ForecastViewModel) {
    AndroidWeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black,
        ) {
            val forecast by forecastViewModel.forecastState.observeAsState()
            forecast?.let { Weather(it) }
        }
    }
}

@Composable
fun Weather(forecast: Forecast) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        LocationName(name = "Kazan")
        Temperature(value = forecast.current.temperature2m)
        WeatherDescription(value = forecast.current.weathercode)
        Spacer(modifier = Modifier.height(40.dp))
        TenDayForecast()
    }
}

@Composable
fun LocationName(name: String) {
    Text(
        text = name.uppercase(),
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}

@Composable
fun Temperature(value: Double = 0.0) {
    Text(
        text = "$value°",
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 120.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        modifier = Modifier.absoluteOffset(22.dp)
    )
}

@Composable
fun WeatherDescription(value: Int) {
    var description: String
    when (value) {
        0 -> description = "Clear sky"
        1, 2, 3 -> description = "Partly cloudy"
        45, 48 -> description = "Fog"
        51, 53, 55 -> description = "Drizzle"
        56, 57 -> description = "Freezing Drizzle"
        61, 63, 65 -> description = "Rain"
        66, 67 -> description = "Freezing Rain"
        71, 73, 75 -> description = "Snow fall"
        77 -> description = "Snow grains"
        80, 81, 82 -> description = "Rain shower"
        85, 86 -> description = "Snow shower"
        95, 96, 99 -> description = "Thunderstorm"
        else -> description = "Sunny"
    }
    Text(
        text = description,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}

@Composable
fun TenDayForecastRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Today".uppercase(),
            color = Color.White,
        )
        Icon(
            Icons.Rounded.PlayArrow,
            contentDescription = "Weather icon",
            tint = Color.White
        )
        Text(
            text = "23°".uppercase(),
            color = Color.White,
        )
    }
}

@Composable
fun TenDayForecast() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .padding(15.dp)
    ) {
        Text(
            text = "10-Day Forecast".uppercase(),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (index in 0..10) {
                TenDayForecastRow()
            }
        }
    }
}

@Preview()
@Composable
fun WeatherAppPreview() {
    Weather({ } as Forecast)
}