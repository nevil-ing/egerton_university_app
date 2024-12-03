// NewsWorker.kt
package com.coelib.egerton_university_app.utils.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coelib.egerton_university_app.data.api_service.RetrofitNewsInstance
import com.coelib.egerton_university_app.data.room_database.AppDatabase
import com.coelib.egerton_university_app.data.room_database.NewsEntity
import com.coelib.egerton_university_app.data.room_database.NoticeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    private val newsDao = AppDatabase.getDatabase(appContext).newsDao()
    private val noticeDao = AppDatabase.getDatabase(appContext).noticeDao()
    private val newsService = RetrofitNewsInstance.newsService

    override fun doWork(): Result {
        // Use a coroutine scope to perform suspend functions in doWork()
        CoroutineScope(Dispatchers.IO).launch {
            fetchAndStoreNews()
            fetchAndStoreNotices()
        }
        return Result.success()
    }

    private suspend fun fetchAndStoreNews() {
        try {
            val news = newsService.getNewsModel()
            val newsEntities = news.map {
                NewsEntity(
                    id = it.id,
                    title = it.title,
                    date = it.date,
                    intro = it.intro,
                    imageUrl = it.imageUrl,
                    link = it.link
                )
            }
            newsDao.insertAll(newsEntities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchAndStoreNotices() {
        try {
            val notices = newsService.getNoticesModel()
            val noticeEntities = notices.map {
                NoticeEntity(
                    id = it.id,
                    title = it.title,
                    article = it.article,
                    date = it.date
                )
            }
            noticeDao.insertAll(noticeEntities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
