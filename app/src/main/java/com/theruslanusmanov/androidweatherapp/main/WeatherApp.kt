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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.ui.theme.fontFamily
import com.theruslanusmanov.androidweatherapp.viewmodel.ForecastViewModel
import java.text.SimpleDateFormat
import java.util.Locale


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
        WeatherDescription(weathercode = forecast.current.weathercode)
        Spacer(modifier = Modifier.height(40.dp))
        TenDayForecast(dailyForecast = forecast.daily)
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
fun WeatherDescription(weathercode: Int) {
    Text(
        text = getWeatherCodeDescription(weathercode),
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
fun TenDayForecastRow(
    time: Int,
    weatherCode: Int,
    temperature: Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = getDayOfWeek(time),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
        Text(
            text = getWeatherCodeDescription(weatherCode).uppercase(),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            painter = painterResource(id = getWeatherIcon(weatherCode)),
            contentDescription = "Weather icon",
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = "${String.format("%.1f", temperature)}°".uppercase(),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun TenDayForecast(dailyForecast: Daily) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .padding(15.dp)
    ) {
        Text(
            text = "10-Day Forecast".uppercase(), fontWeight = FontWeight.Bold, color = Color.White
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (index in 0..9) {
                TenDayForecastRow(
                    dailyForecast.time[index],
                    dailyForecast.weathercode[index],
                    (dailyForecast.temperature2mMax[index] + dailyForecast.temperature2mMin[index]) / 2
                )
            }
        }
    }
}

fun getWeatherCodeDescription(code: Int): String {
    when (code) {
        0 -> return "Clear sky"
        1, 2, 3 -> return "Partly cloudy"
        45, 48 -> return "Fog"
        51, 53, 55 -> return "Drizzle"
        56, 57 -> return "Freezing Drizzle"
        61, 63, 65 -> return "Rain"
        66, 67 -> return "Freezing Rain"
        71, 73, 75 -> return "Snow fall"
        77 -> return "Snow grains"
        80, 81, 82 -> return "Rain shower"
        85, 86 -> return "Snow shower"
        95, 96, 99 -> return "Thunderstorm"
        else -> return "Sunny"
    }
}

fun getWeatherIcon(weatherCode: Int): Int {
    when (weatherCode) {
        0 -> return R.drawable.ic_sunny
        1, 2, 3 -> return R.drawable.ic_cloud
        45, 48 -> return R.drawable.ic_cloud
        51, 53, 55 -> return R.drawable.ic_rainy
        56, 57 -> return R.drawable.ic_rainy
        61, 63, 65 -> return R.drawable.ic_rainy
        66, 67 -> return R.drawable.ic_rainy
        71, 73, 75 -> return R.drawable.ic_snowflake
        77 -> return R.drawable.ic_snowflake
        80, 81, 82 -> return R.drawable.ic_rainy
        85, 86 -> return R.drawable.ic_snowflake
        95, 96, 99 -> return R.drawable.ic_rainy
        else -> return R.drawable.ic_sunny
    }
}

fun getDayOfWeek(timestamp: Int): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
}

@Preview()
@Composable
fun WeatherAppPreview() {
    Weather({ } as Forecast)
}