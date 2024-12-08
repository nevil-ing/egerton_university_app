package com.coelib.egerton_university_app.data.cloud_store_news

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch

class FirestoreNewsModel : ViewModel() {
    val state = mutableStateOf<List<News>>(emptyList())

    private val db = FirebaseFirestore.getInstance()

    init {
        listenToNewsUpdates()
    }

    // Listen for real-time updates from Firestore
    private fun listenToNewsUpdates() {
        db.collection("news")
            .orderBy("scrapedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirestoreNewsModel", "Error listening for news updates: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val updatedNewsList = snapshot.toObjects(News::class.java)
                    state.value = updatedNewsList
                }
            }
    }
}
