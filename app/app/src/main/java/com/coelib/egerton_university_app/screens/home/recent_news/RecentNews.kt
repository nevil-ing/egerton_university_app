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

@Composable
fun RecentNewsView(newsViewModel: NewsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var recentNewsList by remember { mutableStateOf<List<NewsModelItemX>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                recentNewsList = newsViewModel.getNews()
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        RecentNewsList(newsItems = recentNewsList)
    }
}

@Composable
fun RecentNewsList(newsItems: List<NewsModelItemX>) {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(newsItems) { newsItem ->
            RecentNewsItemCard(newsItem)
        }
    }
}

@Composable
fun RecentNewsItemCard(newsItem: NewsModelItemX) {
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
