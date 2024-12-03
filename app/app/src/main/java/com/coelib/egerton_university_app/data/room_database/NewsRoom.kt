package com.coelib.egerton_university_app.data.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val date: String,
    val intro: String,
    val imageUrl: String,
    val link: String,


)

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val article: String,
    val date: String,
)

