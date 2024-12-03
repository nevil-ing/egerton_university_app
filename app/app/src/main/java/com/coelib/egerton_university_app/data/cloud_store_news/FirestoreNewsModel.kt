package com.coelib.egerton_university_app.data.cloud_store_news

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class FirestoreNewsModel : ViewModel() {
    val state = mutableStateOf<List<News>>(emptyList())

    init {
        getFireNews()
    }

    private fun getFireNews() {
        viewModelScope.launch {
            state.value = getNewsFromFirestore()
        }
    }

    private suspend fun getNewsFromFirestore(): List<News> {
        val db = FirebaseFirestore.getInstance()
        val newsList = mutableListOf<News>()

        try {
            val result = db.collection("news").orderBy("scrapedAt").get().await()
            result.documents.mapNotNullTo(newsList) { it.toObject(News::class.java) }
        } catch (e: FirebaseFirestoreException) {
            Log.e("FirestoreNewsModel", "Error fetching news: ${e.message}")
        }

        return newsList
    }
}
