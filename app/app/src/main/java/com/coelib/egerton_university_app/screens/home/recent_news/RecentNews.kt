package com.coelib.egerton_university_app.screens.home.recent_news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.screens.news.NewsViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.coelib.egerton_university_app.components.shimmerBrush
import com.coelib.egerton_university_app.data.recent_news_model.RecentNewsModelItem
import com.coelib.egerton_university_app.utils.Utils


@Composable
fun RecentNewsView(newsViewModel: NewsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val newsData by newsViewModel.newsData.observeAsState(Utils.Loading())
    val snackbarHostState = remember { SnackbarHostState() }


    // Scaffold containing the SnackbarHost
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = { padding ->
            when (newsData) {
                is Utils.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                            .padding(padding)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Utils.Success -> {

                    val recentNewsList = (newsData as Utils.Success<List<NewsModelItemX>>).data ?: emptyList()
                    RecentNewsList(newsItems = recentNewsList)
                }
                is Utils.Error -> {
                                        LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar( "An error occurred")
                    }
                }
            }
        }
    )
}



@Composable
fun RecentNewsList(newsItems: List<NewsModelItemX>) {
    val limitedNewsItems = newsItems.take(8)

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(limitedNewsItems) { newsItem ->
            RecentNewsItemCard(newsItem)
        }
    }
}


@Composable
fun RecentNewsItemCard(newsItem: NewsModelItemX) {
    val showShimmer = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(210.dp)
            .height(300.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            // Check if image URL is valid before trying to load
            if (newsItem.imageUrl.isNullOrEmpty()) {
                // Show placeholder if no image URL
                Image(
                    painter = painterResource(id = R.drawable.placeholderimage),
                    contentDescription = "Placeholder Image",
                    modifier = Modifier
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                )
            } else {

                Image(
                    painter = rememberAsyncImagePainter(
                        model = newsItem.imageUrl,
                        onSuccess = {
                            // Image loaded successfully, hide shimmer
                            showShimmer.value = false
                        },
                        onError = {
                            // Hide shimmer on error and show error placeholder
                            showShimmer.value = false
                        },
                        placeholder = painterResource(id = R.drawable.placeholderimage),
                    ),
                    contentDescription = "news image",
                    modifier = Modifier
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = showShimmer.value
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = newsItem.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = newsItem.intro,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4
            )
        }
    }
}
