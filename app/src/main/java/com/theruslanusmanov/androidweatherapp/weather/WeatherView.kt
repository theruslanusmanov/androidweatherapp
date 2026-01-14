package com.theruslanusmanov.androidweatherapp.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.ui.theme.weatherTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Locale

val textColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherView(
    onSearch: () -> Unit,
    weatherViewModel: WeatherViewModel = hiltViewModel<DefaultWeatherViewModel>()
) {
    val uiState by weatherViewModel.uiState.collectAsStateWithLifecycle()
    val locationName by weatherViewModel.locationName.collectAsStateWithLifecycle()
    // pull to refresh
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1500)
            weatherViewModel.refresh()
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            item {
                // header
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search icon",
                        tint = textColor,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onSearch()
                            }
                    )
                }

                Spacer(Modifier.height(32.dp))
            }
            // main info
            item {
                when (val state = uiState) {
                    WeatherViewState.Loading -> {
                        val itemModifier = Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.DarkGray)
                        repeat(1) {
                            Spacer(modifier = itemModifier)
                        }
                    }

                    is WeatherViewState.Success -> {
                        Column {
                            Date()
                            LocationName(name = locationName)
                            Temperature(value = state.data.current?.temperature2m)
                            state.data.current?.weathercode?.let {
                                WeatherDescription(weathercode = it)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
            // 10-day forecast
            item {
                Text(
                    text = "Daily",
                    color = textColor,
                    textAlign = TextAlign.Start,
                    style = weatherTypography.headlineLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            when (val state = uiState) {
                WeatherViewState.Loading -> {
                    val itemModifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.DarkGray)
                    items(10) {
                        Spacer(modifier = itemModifier)
                    }
                }

                is WeatherViewState.Success -> {
                    state.data.let { data ->
                        items(10) { index ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                data.daily?.time[index]?.let {
                                    val instant =
                                        Instant.fromEpochSeconds(it.toLong())
                                    val dateTime = instant.toLocalDateTime(TimeZone.UTC)
                                    DateButton(
                                        dayWeek = dateTime.dayOfWeek.name.take(3),
                                        day = dateTime.dayOfMonth.toString(),
                                        month = dateTime.month.name.take(3)
                                    )
                                }
                                Spacer(Modifier.width(20.dp))
                                DateForecast(
                                    weatherCode = data.daily?.weathercode[index] ?: 0,
                                    maxTemperature = data.daily?.temperature2mMax[index]?.toInt()
                                        .toString(),
                                    minTemperature = data.daily?.temperature2mMin[index]?.toInt()
                                        .toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Date(date: String = "Today") {
    Text(
        text = date,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        color = Color(253, 131, 131, 191),
        style = weatherTypography.headlineSmall,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 28.dp)
    )
}

@Composable
fun LocationName(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = weatherTypography.headlineMedium,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 20.dp)
    )
}

@Composable
fun Temperature(value: Double? = 0.0) {
    Text(
        text = "${value?.toInt()}°",
        color = Color.White,
        textAlign = TextAlign.Center,
        style = weatherTypography.displayLarge,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun WeatherDescription(weathercode: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = -20.dp)
    ) {
        Icon(
            painter = painterResource(id = getWeatherIcon(weathercode)),
            contentDescription = "Weather icon",
            tint = textColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = getWeatherCodeDescription(weathercode),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            style = weatherTypography.headlineMedium,
            color = Color.White
        )
    }
}

fun getWeatherCodeDescription(code: Int): String {
    return when (code) {
        0 -> "Clear sky"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Fog"
        51, 53, 55 -> "Drizzle"
        56, 57 -> "Freezing Drizzle"
        61, 63, 65 -> "Rain"
        66, 67 -> "Freezing Rain"
        71, 73, 75 -> "Snow fall"
        77 -> "Snow grains"
        80, 81, 82 -> "Rain shower"
        85, 86 -> "Snow shower"
        95, 96, 99 -> "Thunderstorm"
        else -> "Sunny"
    }
}

fun getWeatherIcon(weatherCode: Int): Int {
    return when (weatherCode) {
        0 -> R.drawable.ic_sunny
        1, 2, 3 -> R.drawable.ic_cloud
        45, 48 -> R.drawable.ic_cloud
        51, 53, 55 -> R.drawable.ic_rainy
        56, 57 -> R.drawable.ic_rainy
        61, 63, 65 -> R.drawable.ic_rainy
        66, 67 -> R.drawable.ic_rainy
        71, 73, 75 -> R.drawable.ic_snowflake
        77 -> R.drawable.ic_snowflake
        80, 81, 82 -> R.drawable.ic_rainy
        85, 86 -> R.drawable.ic_snowflake
        95, 96, 99 -> R.drawable.ic_rainy
        else -> R.drawable.ic_sunny
    }
}

fun getDayOfWeek(timestamp: Int): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
}

@Composable
fun DateButton(dayWeek: String, day: String, month: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            //.clip(RoundedCornerShape(100.dp, 100.dp, 100.dp, 100.dp))
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = Color(253, 131, 131, 191),
                shape = RoundedCornerShape(100.dp)
            )
    ) {
        Text(
            text = dayWeek,
            color = Color(253, 131, 131, 191),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.offset(0.dp, 12.dp)
        )
        Text(
            color = textColor,
            text = day,
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
        )
        Text(
            color = Color(255, 255, 255, 191),
            text = month,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.offset(0.dp, -12.dp)
        )
    }
}

@Composable
fun DateForecast(weatherCode: Int, maxTemperature: String, minTemperature: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = getWeatherIcon(weatherCode)),
            contentDescription = "Weather icon",
            tint = textColor,
            modifier = Modifier.size(30.dp)
        )
        Text(
            color = textColor,
            text = "MIN",
            textAlign = TextAlign.Center,
            style = weatherTypography.bodySmall,
        )
        Text(
            color = textColor,
            text = maxTemperature,
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
        )
        Text(
            color = textColor,
            text = "MAX",
            textAlign = TextAlign.Center,
            style = weatherTypography.bodySmall,
        )
        Text(
            color = textColor,
            text = minTemperature,
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    name = "Weather screen",
    group = "Screen"
)
@Composable
fun WeatherViewPreview() {
    AndroidWeatherAppTheme {
        WeatherView(
            weatherViewModel = hiltViewModel<FakeWeatherViewModel>(),
            onSearch = {}
        )
    }
}

