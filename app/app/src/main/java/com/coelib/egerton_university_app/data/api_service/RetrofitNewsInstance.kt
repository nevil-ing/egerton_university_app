package com.coelib.egerton_university_app.data.api_service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNewsInstance {
    private const val BASE_URL = "http://10.10.15.223:5000/api/news/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }
}