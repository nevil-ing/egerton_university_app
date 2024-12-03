package com.coelib.egerton_university_app.data.cloud_store_news

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StudentFormModel : ViewModel() {
    val state = mutableStateOf<List<studentDownloads>>(emptyList())

    init {
        getForms()
    }
    private fun getForms(){
        viewModelScope.launch {
            state.value = getStudentForm()
        }
    }
    private suspend fun getStudentForm(): MutableList<studentDownloads> {
        val db = FirebaseFirestore.getInstance()
        val formList = mutableListOf<studentDownloads>()
        try {
            val result = db.collection("studentDownloads").get().await()
            result.documents.mapNotNullTo(formList){it.toObject(studentDownloads::class.java)}
        } catch (e: FirebaseFirestoreException){
            Log.e("StudentModel", "Error fetching student forms: ${e.message}")
        }
        return formList
    }
}

