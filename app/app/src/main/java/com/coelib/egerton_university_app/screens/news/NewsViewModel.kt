package com.coelib.egerton_university_app.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.news_model.NewsModel
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import kotlinx.coroutines.launch
import kotlin.math.log

class NewsViewModel : ViewModel() {
    private val _newsData = MutableLiveData("No Data")
    val newsData: LiveData<String> get() = _newsData
    private val newsService = RetrofitNewsInstance.newsService
    init {
        viewModelScope.launch {
            getNews()
        }
    }
    suspend fun  getNews(): List<NewsModelItemX> {
        _newsData.value = RetrofitNewsInstance.newsService.getNewsModel().toString()
        return newsService.getNewsModel()
    }
}