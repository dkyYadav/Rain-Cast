package com.example.raincast.data.repoImpl

import com.example.raincast.data.remote.WeatherDTO
import com.example.raincast.data.service.WeatherApiService
import com.example.raincast.domain.repository.weather.WeatherRepository

/*repoImpl ka matlab hai Repository Implementation
Isme hum domain layer ke WeatherRepository ka actual implementation likhte hain using API, Room, etc.*/
class WeatherRepositoryImpl(
    private val  apiService: WeatherApiService
    /*Tum is class ko jab use karoge (jaise ViewModel ke andar), to tumhe WeatherApiService dena padega

This is called Dependency Injection (Clean architecture mein common practice)*/
): WeatherRepository{
    /*WeatherRepositoryImpl ek class hai jo WeatherRepository interface ko implement karti hai.
    Iska kaam hai: API call ko handle karna using WeatherApiService.*/

    override suspend fun getWeather(city: String): WeatherDTO {
        return  apiService.getWeather(city)
        /*
Ye function WeatherRepository interface ka implementation hai

Jo bhi city pass karo, uske liye apiService.getWeather(city) call karega

WeatherDTO return karega (jo OpenWeather API se aata hai)*/
    }

}