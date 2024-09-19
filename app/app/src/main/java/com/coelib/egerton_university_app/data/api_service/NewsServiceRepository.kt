package com.coelib.egerton_university_app.data.api_service

import com.coelib.egerton_university_app.data.news_model.NewsModel
import com.coelib.egerton_university_app.data.news_model.NewsModelItem

class NewsServiceRepository {
    private val newsService = RetrofitNewsInstance.newsService
    suspend fun  getNewsService(): List<NewsModelItem> {
        return newsService.getNewsModel()
    }
}