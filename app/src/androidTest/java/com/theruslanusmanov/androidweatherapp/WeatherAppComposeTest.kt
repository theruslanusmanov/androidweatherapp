package com.theruslanusmanov.androidweatherapp

import androidx.compose.ui.test.junit4.createComposeRule
import com.theruslanusmanov.androidweatherapp.data.repository.WeatherRepository
import com.theruslanusmanov.androidweatherapp.main.WeatherApp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.viewmodel.WeatherViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WeatherAppComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity
    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var repo: WeatherRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(repo)
    }

    /**
     * Shows Temperature.
     */
    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            AndroidWeatherAppTheme {
                WeatherApp(viewModel)
            }
        }
    }
}