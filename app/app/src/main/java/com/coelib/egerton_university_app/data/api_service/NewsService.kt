package com.coelib.egerton_university_app.data.api_service

import com.coelib.egerton_university_app.data.news_model.NewsModel
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.data.notice_model.NoticeModelItem
import com.coelib.egerton_university_app.data.recent_news_model.RecentNewsModelItem

import retrofit2.http.GET

interface NewsService {
    @GET("news")
    suspend fun getNewsModel(): List<NewsModelItemX>

    //get recent news
    @GET("recent_news")
    suspend fun  getRecentNewsModel(): List<RecentNewsModelItem>
    //get notices
    @GET("notices")
    suspend fun  getNoticesModel(): List<NoticeModelItem>

}