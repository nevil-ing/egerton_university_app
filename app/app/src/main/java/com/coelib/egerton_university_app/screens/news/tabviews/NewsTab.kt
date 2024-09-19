package com.coelib.egerton_university_app.screens.news.tabviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import com.coelib.egerton_university_app.screens.news.NewsViewModel

@Composable
fun NewsTab(viewModel: NewsViewModel){
    val news by viewModel.news.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }
println(news)
    Column {
        if (news.isEmpty()){
            Text(text = "loading...")
        }else {
            LazyColumn {
                items(news) {
                    newl ->
                    println(newl.title)
                    Text(text = newl.title)

                }
            }
        }
    }

}