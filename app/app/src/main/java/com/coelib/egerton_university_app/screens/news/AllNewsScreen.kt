package com.coelib.egerton_university_app.screens.news

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.components.shimmerBrush
import com.coelib.egerton_university_app.data.cloud_store_news.FirestoreNewsModel
import com.coelib.egerton_university_app.data.cloud_store_news.News

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNewsScreen(
    firestoreNewsModel: FirestoreNewsModel = viewModel(),
    navigateBack: () -> Unit
) {
    val newsList = firestoreNewsModel.state.value
 Scaffold (
     topBar = {
         TopAppBar(title = { /*TODO*/
         Text(text = "Egerton News")},
             navigationIcon = {
                 IconButton(onClick = navigateBack) {
                     Icon(
                         imageVector = Icons.Filled.KeyboardArrowLeft,
                         contentDescription = "Back"
                     )
                 }
             }

         )
     }
 ){paddingValues ->
     Box(modifier = Modifier
         .fillMaxSize()
         .padding(paddingValues)){
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
     }
 }

}

@Composable
fun NewsItem(news: News) {
    val showShimmer = remember { mutableStateOf(true) }
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp)) {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(news.link)
            }
            context.startActivity(intent)
        },
            ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = news.imageUrl,
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
