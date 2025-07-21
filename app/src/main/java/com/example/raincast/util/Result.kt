package com.example.raincast.util

//used of result
//Jab bhi tum koi API call karte ho, toh usmein 4 states ho sakte hain:
//async operations (like API calls) ke result ko handle karne ke liye hota hai.

/*Sealed class ek restricted class hierarchy hoti hai.
Sirf limited subclasses allow hoti hain (jo usi file mein define hoti hain).*/
sealed class Result <out T>{
    object Inttial : Result<Nothing>()
    object Loadding : Result<Nothing>()
    data class success<T>(val data:T) : Result<T>() //T means kisi bhi type ka data ho sakta hai (generic)
    data class Error (val message: String): Result<Nothing>()

}

/*OBject
object ka matlab hota hai singleton — iska sirf ek instance hota hai poore app mein.

Iska use tab hota hai jab tumhe koi value ya state represent karni ho without data.

DAta Class
data class ek special class hoti hai jo sirf data ko hold karti hai.

 Result<nothinf>

 Nothing kya hai?
Nothing Kotlin ka ek special type hai.

Iska matlab hai: koi value kabhi return nahi hogi.

Mostly use hota hai error ya loading states ke liye jahan koi useful data nahi hota.
 */