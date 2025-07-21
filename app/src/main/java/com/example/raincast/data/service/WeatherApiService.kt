package com.example.raincast.data.service

import com.example.raincast.data.remote.WeatherDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/*Jab is class ka object banega, tumhe API key deni padegi

Ye class sirf ek kaam karti hai: weather fetch karna*/
class WeatherApiService(private val  apikey: String) {

    // HttpClient Setup
    private val client = HttpClient(CIO){
       // Content Negotiation
        /*API response mein extra fields ho toh ignore karo
            Loose JSON bhi accept karo (isLenient = true)*/
        install(ContentNegotiation){
            json(Json{
                ignoreUnknownKeys = true //ignore unknown kay api url
                isLenient = true
            })

        }

        // dealy per click
        //Agar internet slow ho ya response delay ho jaye, toh max 15 seconds ka timeout lagaya gaya hai
        install(HttpTimeout){
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        //Base URL automatically set kar diya gaya hai:
        defaultRequest {
            url{
                protocol = URLProtocol.HTTPS // https is manule pass for secqurity resion
                host = "api.openweathermap.org" // url of api base url
            }
        }
    }  //https://api.openweathermap.org/data/2.5/weather?q=Noida&appid=f451542febb21c67c62945b581717d7e

    //Yeh ek suspend function hai, jo coroutine ke andar chalega (async).
    suspend fun getWeather(city : String): WeatherDTO{
        return try {
            client.get("data/2.5/weather"){
                parameter("q", city)
                parameter("appid", apikey)
                parameter("units", "metric") // Added units parameter for Celsius
            }.body() // for get data from givien bunddle
            /*Yeh API call banata hai kuch aise:
            https://api.openweathermap.org/data/2.5/weather?q=Noida&appid=your_api_key&units=metric
             units=metric ka matlab Celsius mein temperature milega
            .body() ka matlab: response ko WeatherDTO object mein convert karna*/
        } catch (e : Exception){
            //log the exceptio for debugging
            e.printStackTrace()
            throw Exception("Failed to fetech weather data: ${e.localizedMessage}")
        }/*Agar koi error aata hai (network ya parsing), toh usse print karega aur message ke sath dubara throw karega*/

    }

}