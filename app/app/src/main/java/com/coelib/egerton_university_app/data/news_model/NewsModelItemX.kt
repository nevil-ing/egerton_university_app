package com.coelib.egerton_university_app.data.news_model

import com.squareup.moshi.Json

data class NewsModelItemX(
    @Json(name = "Date") val date: String,
    @Json(name = "Intro") val intro: String,
    @Json(name = "Image_url") val imageUrl: String,
    @Json(name = "Link") val link: String,
    @Json(name = "Title") val title: String,
    @Json(name = "id") val id: Int
)
