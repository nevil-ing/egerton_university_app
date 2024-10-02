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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.coelib.egerton_university_app.screens.news.NewsViewModel
import com.coelib.egerton_university_app.screens.news.NoticeViewModel
import com.coelib.egerton_university_app.utils.Utils

@Composable
fun NoticeTab(noticeViewModel: NoticeViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val noticeData by noticeViewModel.noticesData.observeAsState(Utils.Loading())


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {padding ->
            when (noticeData) {
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
                    val noticesList = (noticeData as Utils.Success<List<NoticeModelItem>>).data ?: emptyList()
                    NoticeList(noticeItems = noticesList )
                }
                is Utils.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar((noticeData as Utils.Error).message ?: "An error occurred")
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

