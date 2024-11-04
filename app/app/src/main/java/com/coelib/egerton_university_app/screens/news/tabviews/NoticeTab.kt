package com.coelib.egerton_university_app.screens.news.tabviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.data.news_model.NewsModelItemX
import com.coelib.egerton_university_app.data.notice_model.NoticeModelItem
import com.coelib.egerton_university_app.screens.home.recent_news.RecentNewsList
import com.coelib.egerton_university_app.screens.news.NewsViewModel
import com.coelib.egerton_university_app.screens.news.NoticeViewModel
import com.coelib.egerton_university_app.utils.Utils
import com.coelib.egerton_university_app.utils.networkUtils.ConnectivityObserver
import kotlinx.coroutines.launch

@Composable
fun NoticeTab(noticeViewModel: NoticeViewModel = viewModel(), connectivityObserver: ConnectivityObserver) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val connectivityStatus by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available)


    val noticeData by noticeViewModel.noticesData.observeAsState(Utils.Loading())


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {padding ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        when (noticeData) {
                            is Utils.Loading -> {
                                CircularProgressIndicator()
                            }
                            is Utils.Success -> {
                                val noticeList = (noticeData as Utils.Success<List<NoticeModelItem>>).data ?: emptyList()
                                NoticeList(noticeItems = noticeList)
                            }
                            is Utils.Error -> {
                                // Show error message and refresh button
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "An error occurred. Please try again.")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    IconButton(
                                        onClick = { coroutineScope.launch { noticeViewModel.getNotices() } }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_refresh),
                                            contentDescription = "Refresh",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    }
                                }
                                // Show Snackbar on Error
                                LaunchedEffect(snackbarHostState) {
                                    snackbarHostState.showSnackbar("An error occurred.")
                                }
                            }
                        }


                        if (connectivityStatus == ConnectivityObserver.Status.Available && noticeData is Utils.Error) {
                            LaunchedEffect(connectivityStatus) {
                                noticeViewModel.getNotices()
                            }
                        }
                    }
                }

    )
}

@Composable
fun NoticeList(noticeItems: List<NoticeModelItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(noticeItems) { noticeItems ->
            NoticeItemCard(noticeItems)
        }
    }
}

@Composable
fun NoticeItemCard(noticeItem: NoticeModelItem) {
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
            /*
            Image(
                painter = rememberAsyncImagePainter(
                    model = noticeItem.,
                    placeholder = painterResource(id = R.drawable.placeholderimage),
                ),
                contentDescription = "image_url",
                modifier = Modifier
            )

             */

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = noticeItem.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = noticeItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = noticeItem.article,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

