package com.coelib.egerton_university_app.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coelib.egerton_university_app.data.api_service.NewsServiceRepository
import com.coelib.egerton_university_app.data.news_model.NewsModelItem
import kotlinx.coroutines.launch
import kotlin.math.log

class NewsViewModel : ViewModel() {
    private  val repository = NewsServiceRepository()
    private val _news = MutableLiveData<List<NewsModelItem>>()
    val news: LiveData<List<NewsModelItem>> = _news
    fun fetchNews(){
        viewModelScope.launch {
            try {
                val newsCards = repository.getNewsService()
                _news.value = newsCards
            } catch (_: Exception){
               //error handling
            }
        }
    }
}