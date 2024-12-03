package com.coelib.egerton_university_app.screens.news.tabviews

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.components.shimmerBrush
import com.coelib.egerton_university_app.data.cloud_store_news.FirestoreNewsModel
import com.coelib.egerton_university_app.data.cloud_store_news.News
import com.coelib.egerton_university_app.utils.networkUtils.ConnectivityObserver



@Composable
fun NewsTab(
    firestoreNewsModel: FirestoreNewsModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    connectivityObserver: ConnectivityObserver
) {
    val newsList = firestoreNewsModel.state.value
    val coroutineScope = rememberCoroutineScope()
    val connectivityStatus by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (connectivityStatus != ConnectivityObserver.Status.Available) {

                Text(text = "No internet connection. Please reconnect.")
            }else {
                if (newsList.isEmpty()) {

                    CircularProgressIndicator()
                } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(newsList.size) { index ->
                        val news = newsList[index]
                        NewsItem(news = news)
                    }
                }
                }}
            }
        }
}

@Composable
fun NewsItem(news: News) {
    val showShimmer = remember { mutableStateOf(true) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(news.link)
                }
                context.startActivity(intent)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = news.imageUrl,
                    onSuccess = {
                        showShimmer.value = false
                    },
                    onError = {
                        showShimmer.value = false
                    },
                    onLoading = {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = news.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3
                )
                Text(
                    text = news.intro,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4
                )
            }
        }
    }
}
