package com.coelib.egerton_university_app.screens.home.recent_news

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import com.coelib.egerton_university_app.components.shimmerBrush
import com.coelib.egerton_university_app.data.cloud_store_news.FirestoreNewsModel
import com.coelib.egerton_university_app.data.cloud_store_news.News
import com.coelib.egerton_university_app.utils.Utils
import com.coelib.egerton_university_app.utils.networkUtils.ConnectivityObserver


@Composable
fun RecentNewsView(
    firestoreNewsModel: FirestoreNewsModel = viewModel(), // Use the FirestoreNewsModel
    connectivityObserver: ConnectivityObserver
) {
    val newsList = firestoreNewsModel.state.value // Observing FirestoreNewsModel
    val snackbarHostState = remember { SnackbarHostState() }
    val connectivityStatus by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (connectivityStatus != ConnectivityObserver.Status.Available) {

                    Text(text = "No internet connection. Please reconnect.")
                } else {
                    if (newsList.isEmpty()) {
                        // Show loading state
                        CircularProgressIndicator()
                    } else {
                        // Show the news list
                        RecentNewsList(newsItems = newsList)
                    }
                }
            }
        }
    )
}

@Composable
fun RecentNewsList(newsItems: List<News>) {
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
fun RecentNewsItemCard(newsItem: News) {
    val showShimmer = remember { mutableStateOf(true) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(210.dp)
            .height(300.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(newsItem.link)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = newsItem.imageUrl,
                    onSuccess = { showShimmer.value = false },
                    onError = { showShimmer.value = false },
                    placeholder = painterResource(id = R.drawable.placeholderimage)
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
