package com.coelib.egerton_university_app.data.notice_model

import com.squareup.moshi.Json

data class NoticeModelItem(
    @Json(name= "Article") val article: String,
    @Json(name = "Date") val date: String,
    @Json(name = "Title") val title: String,
    @Json(name = "id") val id: Int
)