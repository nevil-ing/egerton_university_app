package com.coelib.egerton_university_app.data.recent_news_model

import com.squareup.moshi.Json

data class RecentNewsModelItem(
    @Json(name = "Date") val date: String? = "No date available", // Optional with default value
    @Json(name = "Image_url") val imageUrl: String,
    @Json(name = "Link") val link: String,
    @Json(name = "Title") val title: String,
    @Json(name = "id") val id: Int
)