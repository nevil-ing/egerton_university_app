/*
package com.coelib.egerton_university_app.di

import com.coelib.egerton_university_app.data.NewsRepository
import com.coelib.egerton_university_app.data.api_service.NewsService
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.room_database.AppDatabase
import com.coelib.egerton_university_app.screens.news.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    // Provide NewsService instance from RetrofitNewsInstance
    single<NewsService> { RetrofitNewsInstance.newsService }

    // Provide NewsDao instance from Room database
    single { AppDatabase.getDatabase(androidContext()).newsDao() }

    // Provide NewsRepository instance with NewsDao and NewsService as parameters
    single { NewsRepository(get(), get()) }

    // Provide NewsViewModel instance with NewsRepository as parameter
    viewModel { NewsViewModel(get()) }
}


 */