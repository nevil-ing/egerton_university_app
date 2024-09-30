package com.coelib.egerton_university_app.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.news_model.NewsModel
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.utils.Utils
import kotlinx.coroutines.launch
import kotlin.math.log


class NewsViewModel : ViewModel() {
    private val _newsData = MutableLiveData<Utils<List<NewsModelItemX>>>()
    val newsData: LiveData<Utils<List<NewsModelItemX>>> get() = _newsData
    private val newsService = RetrofitNewsInstance.newsService

    init {
        viewModelScope.launch {
            getNews()
        }
    }

    suspend fun getNews(): Utils<List<NewsModelItemX>> {
        return try {
            val response = newsService.getNewsModel()
            _newsData.value = Utils.Success(response)
            Utils.Success(response)
        } catch (e: Exception) {
            _newsData.value = Utils.Error(e.message ?: "Unknown error")
            Utils.Error(e.message ?: "Unknown error")
        }
    }
}
