package com.theruslanusmanov.androidweatherapp

import androidx.compose.ui.test.junit4.createComposeRule
import com.theruslanusmanov.androidweatherapp.ui.WeatherApp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.viewmodel.WeatherViewModel
import org.junit.Rule
import org.junit.Test

class WeatherAppComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    /**
     * Shows Temperature.
     */
    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            AndroidWeatherAppTheme {
                WeatherApp(null as WeatherViewModel)
            }
        }
    }
}