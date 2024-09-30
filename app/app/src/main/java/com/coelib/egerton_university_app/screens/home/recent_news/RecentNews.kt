package com.coelib.egerton_university_app.screens.home.recent_news

import androidx.compose.foundation.Image
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
import com.coelib.egerton_university_app.data.recent_news_model.RecentNewsModelItem
import com.coelib.egerton_university_app.utils.Utils


@Composable
fun RecentNewsView(recentNewsViewModel: RecentNewsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val newsData by recentNewsViewModel.recentNewsData.observeAsState(Utils.Loading())
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(true) }

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
                    isLoading = false
                    val recentNewsList = (newsData as Utils.Success<List<RecentNewsModelItem>>).data ?: emptyList()
                    RecentNewsList(newsItems = recentNewsList)
                }
                is Utils.Error -> {
                    // Show a Snackbar when there's an error
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar( "An error occurred")
                    }
                }
            }
        }
    )
}



@Composable
fun RecentNewsList(newsItems: List<RecentNewsModelItem>) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding to the LazyRow itself
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between items
    ) {
        items(newsItems) { newsItem ->
            RecentNewsItemCard(newsItem)
        }
    }
}


@Composable
fun RecentNewsItemCard(newsItem: RecentNewsModelItem) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(200.dp)
            .height(270.dp),
        elevation = CardDefaults.cardElevation(5.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
             // Centers the image and text
        ) {
            // Image at the top
            Image(
                painter = rememberAsyncImagePainter(
                    model = newsItem.imageUrl,
                    placeholder = painterResource(id = R.drawable.placeholderimage),
                ),
                contentDescription = "news image",
                modifier = Modifier
                    .height(150.dp)
                    .size(200.dp) // Fixed image size
                    .clip(RoundedCornerShape(16.dp)) // Rounded edges for the image
                    .fillMaxWidth(),

            )

            Spacer(modifier = Modifier.height(8.dp)) // Space between image and text

            // Text below the image
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 4
            )
        }
    }
}
