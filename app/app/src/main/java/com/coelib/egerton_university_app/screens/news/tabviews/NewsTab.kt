package com.coelib.egerton_university_app.screens.news.tabviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.screens.news.NewsViewModel
import kotlinx.coroutines.launch
import com.coelib.egerton_university_app.R


@Composable
fun NewsTab(newsViewModel: NewsViewModel = viewModel()) {
    // Use a coroutine scope within the composable
    val coroutineScope = rememberCoroutineScope()

    // State to hold the list of news
    var newsList by remember { mutableStateOf<List<NewsModelItemX>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Launch the coroutine to fetch the news data
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                newsList = newsViewModel.getNews()  // Call the suspend function to fetch the news
            } finally {
                isLoading = false  // Stop showing the loading indicator once data is fetched
            }
        }
    }

    // Show loading indicator while fetching data
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Show the list of news items when loading is done
        NewsList(newsItems = newsList)
    }
}

@Composable
fun NewsList(newsItems: List<NewsModelItemX>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(newsItems) { newsItem ->
            NewsItemCard(newsItem)
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsModelItemX) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = newsItem.imageUrl,
                    placeholder = painterResource(id = R.drawable.placeholderimage),
                ),
                contentDescription = "image_url",
                modifier = Modifier

            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                // Display date if available
                newsItem.date?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant )
                }
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

