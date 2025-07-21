package com.example.raincast.domain.repository.weather

import com.example.raincast.data.remote.WeatherDTO
/*✅ App ke core logic ko define karna,
✅ External dependencies se door rehna.*/
interface WeatherRepository {
    suspend fun getWeather(city : String): WeatherDTO
/*suspend ka matlab: ye coroutine ke andar asynchronously chal sakta hai

Input: city (e.g., "Noida", "Delhi")*/
}
/*Interface ek blueprint hoti hai.

Isme function define hote hain, lekin unka implementation nahi hota.

Implementation data layer mein hoti hai — jaise tumne WeatherRepositoryImpl mein kiya hai.*/