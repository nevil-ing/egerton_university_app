package com.coelib.egerton_university_app.data

import androidx.lifecycle.LiveData
import com.coelib.egerton_university_app.data.api_service.NewsService
import com.coelib.egerton_university_app.data.room_database.NewsDao
import com.coelib.egerton_university_app.data.room_database.NewsEntity

class NewsRepository(
    private val newsDao: NewsDao,
    private val apiService: NewsService
) {
    // Fetches cached data from Room
    val newsData: LiveData<List<NewsEntity>> = newsDao.getAllNews()

    // Function to refresh the news data from the network and store it in Room
    suspend fun refreshNews() {
        try {
            val newsFromNetwork = apiService.getNewsModel() // Fetches from network
            val newsEntities = newsFromNetwork.map { newsItem ->
                // Convert to NewsEntity if necessary
                NewsEntity(
                    id = newsItem.id,
                    title = newsItem.title,
                    intro = newsItem.intro,
                    date = newsItem.date,
                    imageUrl = newsItem.imageUrl,
                    link = newsItem.link
                )
            }
            newsDao.insertAll(newsEntities) // Insert into Room
        } catch (e: Exception) {
            // Handle errors as needed
        }
    }
}
