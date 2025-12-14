package com.example.raincast.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val temp: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null
)
/*
@Serializable
*/
/*data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)*//*

data class Main(
    val feels_like: Double,
    val grnd_level: Double,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)*/
