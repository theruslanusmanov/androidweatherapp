package com.theruslanusmanov.androidweatherapp.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.WeatherRoutes


@Composable
fun SearchView(onBack: () -> Unit, searchViewModel: SearchViewModel = hiltViewModel()) {
    val searchResults by searchViewModel.searchState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Icon(
            modifier = Modifier.clickable {
                onBack()
            },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Search icon",
            tint = Color.White,
        )
        // input
        Search(searchText = searchQuery, focused = true, onSearch = {
            searchViewModel.search(it)
        })

        // results
        if (searchResults?.results?.isEmpty() == true) {
            Text(text = "LOADING...", color = Color.White)
        } else {
            searchResults?.let {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                                    // save location
                                    searchViewModel.saveLocation(
                                        Pair(
                                            searchResults!!.results[index].latitude.toString(),
                                            searchResults!!.results[index].longitude.toString()
                                        )
                                    )
                                    searchViewModel.saveLocationName(searchResults!!.results[index].name!!)
                                    // go back
                                    onBack()
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
fun Search(
    searchText: String,
    focused: Boolean = false,
    onSearch: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (focused) focusRequester.requestFocus() // Request focus when the composable enters the composition
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearch,
        placeholder = { Text(text = "Search") },
        singleLine = true,
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = Color.White
            )
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 100))
            .fillMaxWidth()
            .background(Color.DarkGray)
            .focusRequester(focusRequester)
            .testTag("SearchTextField")
    )
}

@Preview(name = "Search", group = "Components", showBackground = true)
@Composable
fun SearchPreview() {
    Search(searchText = "", focused = true, onSearch = {})
}