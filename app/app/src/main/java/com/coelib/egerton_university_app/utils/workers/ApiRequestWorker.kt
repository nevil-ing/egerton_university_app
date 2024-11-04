package com.coelib.egerton_university_app.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.data.notice_model.NoticeModelItem
import com.coelib.egerton_university_app.utils.Utils
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class ApiRequestWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    private val TAG = "ApiRequestWorker"

    override fun doWork(): Result {
        return try {
            // Call the API methods and handle the results
            runBlocking {
                val newsResult = fetchNews()
                val noticesResult = fetchNotices()

                // Here you can handle the results if needed
                Log.d(TAG, "Fetched news: $newsResult")
                Log.d(TAG, "Fetched notices: $noticesResult")
            }
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error in ApiRequestWorker: ${e.message}", e)
            Result.failure()
        }
    }

    private suspend fun fetchNews(): Utils<List<NewsModelItemX>> {
        return try {
            val response = RetrofitNewsInstance.newsService.getNewsModel()
            Utils.Success(response)
        } catch (e: Exception) {
            Utils.Error(e.message ?: "Unknown error")
        }
    }

    private suspend fun fetchNotices(): Utils<List<NoticeModelItem>> {
        return try {
            val response = RetrofitNewsInstance.newsService.getNoticesModel()
            Utils.Success(response)
        } catch (e: Exception) {
            Utils.Error(e.message ?: "Unknown error")
        }
    }
}


