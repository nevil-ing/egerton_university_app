package com.coelib.egerton_university_app.data.api_service

import com.coelib.egerton_university_app.data.news_model.NewsModel
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX

import retrofit2.http.GET

interface NewsService {
    @GET("news")
    suspend fun getNewsModel(): List<NewsModelItemX>
}