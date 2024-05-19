package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.WeatherRoutes

@Composable
fun SearchView(searchViewModel: SearchViewModel, navController: NavController) {
    val searchResults by searchViewModel.searchState.observeAsState()

    Column(modifier = Modifier.padding(20.dp)) {
        if (searchResults?.results?.isEmpty() == true) {
            Text(text = "LOADING...", color = Color.White)
        } else {
            searchResults?.let {
                Log.d("SEARCH", it.toString())
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SearchInput(navController = navController, searchViewModel)
                    for (index in 0 until searchResults!!.results.size) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                                .padding(8.dp, 2.dp)
                                .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                                .background(Color.DarkGray)
                                .clickable {
                                    navController.navigate(WeatherRoutes.Main.name)
                                }
                        ) {
                            Text(
                                text = searchResults!!.results[index].country.toString(),
                                color = Color.White,
                                modifier = Modifier.padding(start = 48.dp)
                            )
                            Text(
                                text = searchResults!!.results[index].name.toString(),
                                color = Color.White,
                                modifier = Modifier.padding(end = 48.dp)
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun SearchInput(navController: NavController, viewModel: SearchViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Search Button
        Box(
            modifier = Modifier
                .width(48.dp)
                .clickable {
                    navController.navigate(WeatherRoutes.Main.name)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Search icon",
                tint = Color.White
            )
        }

        var searchQuery by remember { mutableStateOf("") }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search icon",
                    tint = Color.White
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Gray,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                cursorColor = Color.White
            ),
            shape = RoundedCornerShape(48.dp),
            modifier = Modifier.height(48.dp).weight(1f)
        )
    }
}