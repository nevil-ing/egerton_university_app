package com.coelib.egerton_university_app.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.notice_model.NoticeModelItem
import com.coelib.egerton_university_app.utils.Utils
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {
     private val _noticesData = MutableLiveData<Utils<List<NoticeModelItem>>>()

    val noticesData: LiveData<Utils<List<NoticeModelItem>>> get() = _noticesData
    private val noticesService = RetrofitNewsInstance.newsService

    init {
        viewModelScope.launch { 
            getNotices()
        }
    }

    suspend fun getNotices(): Utils<List<NoticeModelItem>>{
        return try{
            val response = noticesService.getNoticesModel()
            _noticesData.value = Utils.Success(response)
            Utils.Success(response)
        } catch (e: Exception){
            _noticesData.value = Utils.Error(e.message?: "Unknown error")
            Utils.Error(e.message ?: "Unknown error")
        }
    }
}