package com.example.raincast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.raincast.data.repoImpl.WeatherRepositoryImpl
import com.example.raincast.data.service.WeatherApiService
import com.example.raincast.domain.repository.weather.WeatherRepository
import com.example.raincast.presentation.view.Home
import com.example.raincast.presentation.viewModel.WeatherViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    //Yeh tumhara OpenWeatherMap API key hai.
    //
    //WeatherApiService ko ye key lagega taaki woh authorized API calls kar sake.
    private val apikay = "f451542febb21c67c62945b581717d7e"

    //lazy keyword ensure karta hai ki object sirf tab create ho jab zarurat ho.
    //Yani, WeatherApiService tabhi banega jab pehli baar use hoga.
    private val weatherApiService by lazy {
        WeatherApiService(apikay)
    }
    /*Ye domain layer ka interface (WeatherRepository) ko WeatherRepositoryImpl se connect karta hai
    Implementation data layer mein hoti hai, aur interface domain mein*/
    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherApiService)
    }

    private val viewModel: WeatherViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override  fun <T : ViewModel> create(modeClass: Class<T>): T{
             return WeatherViewModel(weatherRepository)as T
            }
            /*Jetpack Compose mein by viewModels ya rememberViewModel() use hota hai, but agar tumhara ViewModel constructor me argument leta hai (like repository), to factory banana padta hai manually.
            Isliye ViewModelProvider.Factory anonymous class banayi gayi hai
             as T cast is necessary because create() function generic hota hai*/

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
       enableEdgeToEdge()
        setContent {
            //ui can access state
            //button can call api
            Home(viewModel = viewModel)
            /*ViewModel ke functions call kar sakte ho
(e.g. viewModel.getWeatherForCity("Noida"))

ViewModel ka state observe kar sakte ho
(e.g. val state = viewModel.weatherState)*/
            //ViewModel = Dimaag (Business logic + Data)

        }
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    RainCastTheme {
        Greeting("Android")
    }
}*/
