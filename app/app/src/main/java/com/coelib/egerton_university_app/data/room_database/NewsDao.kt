package com.coelib.egerton_university_app.data.room_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<NewsEntity>>
}

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notices: List<NoticeEntity>)

    @Query("SELECT * FROM notices")
    fun getAllNotices(): LiveData<List<NoticeEntity>>
}
