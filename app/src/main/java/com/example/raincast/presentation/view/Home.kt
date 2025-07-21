package com.example.raincast.presentation.view

import android.graphics.drawable.Icon
import android.service.notification.Condition
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import com.example.raincast.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raincast.data.remote.Weather
import com.example.raincast.data.remote.WeatherDTO
import com.example.raincast.presentation.viewModel.WeatherViewModel
import com.example.raincast.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: WeatherViewModel) {

    var context = LocalContext.current
    var city by remember { mutableStateOf("") }
    val weatherState = viewModel.weatherState
    val snackbarHostState = remember { SnackbarHostState() }




    LaunchedEffect(key1 = viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearSnackbarMessage()
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        /*Color(0xFF6A5ACD),
                        Color(0xFF20B2AA)*/
                        Color.Blue,    // Top color
                        Color.Red
                    )

                )
            ),
       snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
            title = {
                Text(
                    stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold
                )
            }
          )
        }
    ){innerpadding->

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerpadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top

        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = city,
                    onValueChange = {city = it},
                    label = {
                        Text("Search any City") },
                    singleLine = true //only sign line input
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                       if(city.isNotBlank()){
                          viewModel.getWeatherForCity(city)
                        }else{
                        Toast.makeText(context,"Input is empty!", Toast.LENGTH_SHORT).show()
                       }
                    }
                ) {
                        Text(
                            text =  " Search ",
                            fontSize = 15.sp

                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Result state Handling
        // weather display section

        Box(
            modifier = Modifier.fillMaxSize(), // ⬅️ Take full screen
            contentAlignment = Alignment.Center // ⬅️ Center Column inside
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (weatherState) {
                    is Result.Loadding -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                            Text("Loading weather data...")
                        }

                    }

                    is Result.success -> {
                        val weather = weatherState.data
                        Log.d("Home UI", "Weather Data Fetched:  $weather")

                        WeatherScreen(weather)
                    }

                    is Result.Error -> {
                        Log.d("Home UI", "Showing Error: ${weatherState.message}")
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = "Error: ${weatherState.message}",
                                modifier = Modifier.padding(16.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }


                    is Result.Inttial -> {
                        Log.d("Home UI", "Showing initial state")
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = "Search for a city to see weather data",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                }
            }
        }


    }


}


@Composable
fun WeatherScreen(weather: WeatherDTO) {
    val temperature = weather.main.temp.toInt()
    val weatherCondition = weather.weather.firstOrNull()?.main?: "Unknown"
    val weatherDescription = weather.weather.firstOrNull()?.description?:"Unknown weather condition"
    val humidity = weather.main.humidity
    val windspeed = weather.wind.speed
    Card (
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                ).padding(24.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text =weather.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(text =weather.sys.country,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(14.dp))

            // temperature and weather condition

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$temperature°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = weatherCondition,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = weatherDescription,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
                // Weather icon placeholder
                Box (
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background((MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = getWeatherEmoji(weatherCondition),
                        fontSize = 40.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                // Additional weather details

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_humidity),
                        contentDescription = "pressure Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Humidity: ${weather.main.humidity}%",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_pressure),
                        contentDescription = "pressure Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pressure: ${weather.main.pressure} hpa",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = "Wind Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Wind: $windspeed m/s",
                        fontSize = 14.sp
                    )

                }
        }
    }
}

private fun getWeatherEmoji(condition: String) : String{
    return when(condition.lowercase()){
        "clear" -> "☀️"
        "clouds" -> "☁️"
        "rain" -> "🌧️"
        "drizzle" -> "🌦️"
        "thunderstorm" -> "⛈️"
        "snow" -> "❄️"
        "mist", "fog", "haze" -> "🌫️"
        else->"🌤️"
    }
}

@Preview(showSystemUi = true)
@Composable
fun WeatherPreview() {
  // WeatherScreen()
}




