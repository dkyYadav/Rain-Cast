package com.example.raincast.presentation.view

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raincast.data.remote.WeatherDTO
import com.example.raincast.presentation.viewModel.WeatherViewModel
import com.example.raincast.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: WeatherViewModel) {

    var context = LocalContext.current
    var city by remember { mutableStateOf("") }
    val weatherState = viewModel.weatherState // GET ALL STATE LOADDING ,ERROR,SUCCESS
    val snackbarHostState = remember { SnackbarHostState() }


// RECOMPOSE WHEN SNACKBAR MESSAGE CHANGE
    LaunchedEffect(key1 = viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearSnackbarMessage()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        Image(
            painterResource(R.drawable.weatherbg),
            contentDescription = "Bgi",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column (modifier = Modifier
            .padding( top = 50.dp, )){
            Column(
                modifier = Modifier
                    .padding(start = 25.dp, top = 80.dp, bottom = 50.dp, end = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {

                // Input TextFild
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = {
                        Text("Search any City", color = Color.Black)
                    },
                    singleLine = true, // only single line input
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF184905), // Background
                        unfocusedBorderColor = Color(0xFF184905)
                    )

                    )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        if (city.isNotBlank()) {
                            viewModel.getWeatherForCity(city)
                        } else {
                            Toast.makeText(context, "Input is empty!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF184905), // Background
                        contentColor = Color.White,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(start = 35.dp, end = 35.dp)
                ) {
                    Text(
                        text = "   Search   ",
                        fontSize = 20.sp
                    )
                }


            }
                // After call api

            Column( modifier = Modifier.fillMaxSize()
                .padding(start = 15.dp,  end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {


                when (weatherState) {
                    is Result.Loadding -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Loading weather data...", color = Color.White)
                        }
                    }

                    is Result.success -> {
                        val weather = weatherState.data
                        Log.d("MainScreen UI", "Weather Data Fetched:  $weather")
                        WeatherScreen(weather)
                    }

                    is Result.Error -> {
                        Log.d("MainScreen UI", "Showing Error: ${weatherState.message}")
                        Card(
                            modifier = Modifier.fillMaxWidth(),
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
                        Log.d("MainScreen UI", "Showing initial state")
                        Box(modifier = Modifier,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                    text = "Search a city to see weather data" ,
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                            )

                        }

                    }
                }
            }
        }

    }
}


@Composable
fun WeatherScreen(weather: WeatherDTO
) {
    val temperature = weather.main.temp.toInt()
    val weatherCondition = weather.weather.firstOrNull()?.main ?: "Unknown"
    val weatherDescription = weather.weather.firstOrNull()?.description ?: "Unknown weather condition"
    val humidity = weather.main.humidity
    val windspeed = weather.wind.speed



    Card(
        modifier = Modifier.fillMaxSize()
            .wrapContentHeight().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.Lightyellow)
                ).padding(24.dp)
        ) {
            //name and location
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = weather.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = weather.sys.country,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(14.dp))

            // temperature and weather condition

            Row(
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

                Spacer(modifier = Modifier.width(10.dp))
                // Weather icon placeholder

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                    ){
                        Text(
                            text = getWeatherEmoji(weatherCondition),
                            fontSize = 50.sp
                        )

                        Text(
                            text = weatherCondition,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }


            }

            Text(
                text = weatherDescription,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Additional weather details

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = "pressure Icon",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Humidity",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = " ${weather.main.humidity} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_pressure),
                            contentDescription = "pressure Icon",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pressure",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = " ${weather.main.pressure} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_wind),
                            contentDescription = "Wind Icon",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Wind",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "$windspeed M/S",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }
    }

}
// Function to get weather emoji from condition
private   fun getWeatherEmoji(weatherCondition: String): String {
    return when (weatherCondition.lowercase()) {
        "clear" -> "☀️"
        "clouds" -> "☁️"
        "rain" -> "🌧️"
        "drizzle" -> "🌦️"
        "thunderstorm" -> "⛈️"
        "snow" -> "❄️"
        "mist", "fog", "haze", "smoke", "dust", "sand", "ash", "squall", "tornado" -> "🌫️"
        else -> "🌤️" // default emoji
    }
}






