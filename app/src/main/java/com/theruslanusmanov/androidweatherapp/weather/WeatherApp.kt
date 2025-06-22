package com.theruslanusmanov.androidweatherapp.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.WeatherRoutes
import com.theruslanusmanov.androidweatherapp.ui.theme.weatherTypography
import java.text.SimpleDateFormat
import java.util.Locale

val textColor = Color.White

@Composable
fun WeatherApp(forecastViewModel: ForecastViewModel, navController: NavController) {
    val forecast by forecastViewModel.forecastState.observeAsState()
    forecast?.let { Weather(it, navController) }
}

@Composable
fun Weather(forecast: Forecast, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        Header()
        Date()
        LocationName(name = "Kazan")
        Temperature(value = forecast.current.temperature2m)
        WeatherDescription(weathercode = forecast.current.weathercode)
        Spacer(modifier = Modifier.height(50.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ShortInfoBlock(icon = 1, value = 0.0)
            ShortInfoBlock(icon = 1, value = 0.0)
            ShortInfoBlock(icon = 1, value = 0.0)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Daily",
            color = textColor,
            textAlign = TextAlign.Start,
            style = weatherTypography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )
        SearchButton {
            navController.navigate(WeatherRoutes.Search.name)
        }
        TenDayForecast(dailyForecast = forecast.daily)
    }
}

@Composable
fun Header() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_navigation),
            contentDescription = "Search icon",
            tint = textColor,
            modifier = Modifier
                .rotate(125f)
                .size(48.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = "Search icon",
            tint = textColor,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun Date(date: String = "Today") {
    Text(
        text = date,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        color = Color(253, 131, 131, 191),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun LocationName(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = textColor,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun Temperature(value: Double = 0.0) {
    Text(
        text = "$value°",
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = getWeatherIcon(weathercode)),
            contentDescription = "Weather icon",
            tint = textColor,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = getWeatherCodeDescription(weathercode),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                color = textColor,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
}

@SuppressLint("DefaultLocale")
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
            color = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
        Icon(
            painter = painterResource(id = getWeatherIcon(weatherCode)),
            contentDescription = "Weather icon",
            tint = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = "${String.format("%.1f", temperature)}°".uppercase(),
            color = textColor,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
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
            text = "10-Day Forecast".uppercase(), fontWeight = FontWeight.Bold, color = textColor
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
fun ShortInfoBlock(icon: Int, value: Double = 0.0) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(200.dp))
            .clip(RoundedCornerShape(100.dp, 100.dp, 100.dp, 100.dp))
            .aspectRatio(1f)
    ) {
        Icon(
            painter = painterResource(id = getWeatherIcon(icon)),
            contentDescription = "Weather icon",
            tint = textColor,
            modifier = Modifier.size(48.dp)
        )
        Text(
            color = textColor,
            text = "$value°",
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DateButton() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(200.dp))
            .clip(RoundedCornerShape(100.dp, 100.dp, 100.dp, 100.dp))
            .aspectRatio(1f)
    ) {
        Text(
            text = "Sat",
            color = Color(253, 131, 131, 191),
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            color = textColor,
            text = "13",
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            color = Color(255, 255, 255, 191),
            text = "Feb",
            textAlign = TextAlign.Center,
            style = weatherTypography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SearchButton(onClick: () -> Unit) {
    Button(
        colors = ButtonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search icon",
                tint = textColor
            )
            Text(text = "Search", style = weatherTypography.bodyMedium)
        }
    }
}

@Preview(name = "Weather screen")
@Composable
fun WeatherAppPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        Header()
        Date()
        LocationName(name = "Kazan")
        Temperature(value = -10.0)
        WeatherDescription(weathercode = 0)
        Spacer(modifier = Modifier.height(50.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ShortInfoBlock(icon = 1, value = 0.0)
            ShortInfoBlock(icon = 1, value = 0.0)
            ShortInfoBlock(icon = 1, value = 0.0)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Daily",
            color = textColor,
            textAlign = TextAlign.Start,
            style = weatherTypography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )
        SearchButton {}
    }
}

@Preview(name = "Header")
@Composable
fun HeaderPreview() {
    Header()
}

@Preview(name = "Date")
@Composable
fun DatePreview() {
    Date()
}

@Preview(name = "Location")
@Composable
fun LocationPreview() {
    LocationName(name = "Kazan")
}

@Preview(name = "Temperature")
@Composable
fun TemperaturePreview() {
    Temperature(value = -10.0)
}

@Preview(name = "Weather description")
@Composable
fun WeatherDescriptionPreview() {
    WeatherDescription(weathercode = 95)
}

@Preview(name = "Short info block")
@Composable
fun ShortInfoBlockPreview() {
    ShortInfoBlock(icon = 1, value = 0.0)
}

@Preview(name = "Date button")
@Composable
fun DateButtonPreview() {
    DateButton()
}


@Preview(name = "Search button")
@Composable
fun SearchButtonPreview() {
    SearchButton { }
}
