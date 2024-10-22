package com.coelib.egerton_university_app.screens.news.tabviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.livedata.observeAsState
import com.coelib.egerton_university_app.components.shimmerBrush
import com.coelib.egerton_university_app.utils.Utils

@Composable
fun NewsTab(newsViewModel: NewsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Observing the news data state from ViewModel
    val newsData by newsViewModel.newsData.observeAsState(Utils.Loading())


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {padding ->
            when (newsData) {
                is Utils.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Utils.Success -> {
                    val newsList = (newsData as Utils.Success<List<NewsModelItemX>>).data ?: emptyList()
                    NewsList(newsItems = newsList)
                }
                is Utils.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar((newsData as Utils.Error).message ?: "An error occurred")
                    }
                }
            }
        }
    )
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
    // State to track whether the image is loading
    val showShimmer = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Use Coil's rememberAsyncImagePainter to load the image asynchronously
            Image(
                painter = rememberAsyncImagePainter(
                    model = newsItem.imageUrl,
                    onSuccess = {
                        // When the image loads successfully, hide the shimmer effect
                        showShimmer.value = false
                    },
                    onError = {
                        // Hide shimmer on error and show error image or placeholder
                        showShimmer.value = false
                    },
                    onLoading = {
                        // While the image is loading, shimmer will be shown
                        showShimmer.value = true
                    },
                    placeholder = painterResource(id = R.drawable.placeholderimage),
                ),
                contentDescription = "News Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value
                        )
                    )
            )

            // Text content for the news item
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = newsItem.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3
                )
                Text(
                    text = newsItem.intro,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4
                )
            }
        }
    }
}


