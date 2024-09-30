package com.coelib.egerton_university_app.screens.home.recent_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.recent_news_model.RecentNewsModelItem
import com.coelib.egerton_university_app.utils.Utils
import kotlinx.coroutines.launch

class RecentNewsViewModel : ViewModel() {
    private val _recentNewsData =MutableLiveData<Utils<List<RecentNewsModelItem>>>()

    val recentNewsData: LiveData<Utils<List<RecentNewsModelItem>>> get() = _recentNewsData
    private val recentNewsService = RetrofitNewsInstance.newsService

    init {
        viewModelScope.launch {
            getRecentNews()
        }
    }


    suspend fun getRecentNews(): Utils<List<RecentNewsModelItem>>{
        return try {
            val response = recentNewsService.getRecentNewsModel()
            _recentNewsData.value = Utils.Success(response)
            Utils.Success(response)
        } catch (e: Exception){
            _recentNewsData.value = Utils.Error(e.message ?: "Unknown error")
            Utils.Error(e.message ?: "Unknown error")
        }
    }
}