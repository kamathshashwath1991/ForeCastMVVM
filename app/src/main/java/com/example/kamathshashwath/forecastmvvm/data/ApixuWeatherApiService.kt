package com.example.kamathshashwath.forecastmvvm.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY= "e472bf245cf446dc83b191830180411"

/*
My Query URL: http://api.apixu.com/v1/current.json?key=e472bf245cf446dc83b191830180411&q=New Jersey&lang=en
 */

interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query(value = "q") location: String,
        @Query(value = "lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke() : ApixuWeatherApiService{
             val requestInterceptor = Interceptor{chain ->

                 val url = chain.request()
                     .url()
                     .newBuilder()
                     .addQueryParameter("key", API_KEY)
                     .build()

                 val request = chain.request()
                     .newBuilder()
                     .url(url)
                     .build()

                 return@Interceptor chain.proceed(request)

             }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)

        }
    }
}