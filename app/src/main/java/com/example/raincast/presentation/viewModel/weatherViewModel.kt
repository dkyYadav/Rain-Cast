package com.example.raincast.presentation.viewModel
// UI + Business logic
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raincast.data.remote.WeatherDTO
import com.example.raincast.domain.repository.weather.WeatherRepository
import com.example.raincast.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

/*Ye ViewModel class WeatherRepository ko use karke weather data fetch karta hai.
ViewModel Android Jetpack ka part hai – UI survive karata hai configuration changes mein (like screen rotation).*/
class WeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

//Ye Compose UI ke liye current weather fetch ka status store karta hai.
    var weatherState by mutableStateOf<Result<WeatherDTO>>(Result.Inttial)
        private set
//Ye ek simple string hai, jo error ya warning message ko store karta hai.
    //toast ke through message dikha sakta hai.
    var snackbarMessage by mutableStateOf<String?>(null)
        private set


// User se jo city input aayi hai, uske liye weather data fetch karna via repository.
     fun getWeatherForCity(city: String){
         if (city.isBlank()){
             snackbarMessage = "Please enter a city Name"
             return
             //Agar user ne city naam nahi diya, toh error message set ho jaata hai.
         }
    // viewModelScope.launch: safe way to run async tasks in ViewModel
        viewModelScope.launch {
            //thoda sa animation delay for better UX
            delay(500)
            // Result.Loading se UI mein progress indicator dikhega
            weatherState = Result.Loadding
            Log.d("WeatherViewModel", "Fetching weather for city: $city")

            /*Repository se data milta hai
               Agar success, toh Result.Success set ho jaata hai → UI weather show karega*/
            try {
                val weatherData = weatherRepository.getWeather(city.trim())
                weatherState = Result.success(weatherData)
                Log.d("WeatherViewModel", "Weather data fetched successfully: $weatherData")

                //Alag-alag error messages diye gaye hain depending on API error
            }catch (e: Exception){
                Log.d("WeatherViewModel", "Error fetching weather data", e)
                val errorMessage = when {
                    e.message?.contains("404") == true -> "City not found. Please check the city name."
                    e.message?.contains("401") == true -> "API key is invalid or expired."
                    e.message?.contains("timeout") == true -> "Connection timeout. Please check your internet connection."
                    e.message?.contains("network") == true -> "Network error. Please check your internet connection."
                    else -> e.message ?: "An unknown error occurred"
                }
                weatherState = Result.Error(errorMessage)
                snackbarMessage = errorMessage
                //snackbarMessage set hota hai, jisse UI user ko alert kar sake
            }

        }
    }


/*Ye function UI call karta hai snackbar dismiss hone ke baad
Taaki message repeat na ho*/
    fun clearSnackbarMessage() {
        snackbarMessage = null
    }

}