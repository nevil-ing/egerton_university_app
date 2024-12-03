package com.coelib.egerton_university_app.screens.info

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coelib.egerton_university_app.data.cloud_store_news.FirestoreNewsModel
import com.coelib.egerton_university_app.data.cloud_store_news.StudentFormModel
import com.coelib.egerton_university_app.data.cloud_store_news.studentDownloads
import com.coelib.egerton_university_app.screens.news.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun InfoScreen(studentFormModel: StudentFormModel = viewModel()){
    val formList = studentFormModel.state.value
    Scaffold (
        topBar = {
            TopAppBar(title = { /*TODO*/
                Text(text = "Student Forms")},
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
                items(formList.size) { index ->
                    val forms = formList[index]
                    FormsItem(forms = forms)
                }
            }
        }
    }
}

@Composable
fun FormsItem(forms: studentDownloads){
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .clickable {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(forms.link)
            }
            context.startActivity(intent)
        }) {
        Column(

            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = forms.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3
            )
            Text(
                text = forms.link,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                maxLines = 4
            )
        }
    }
}