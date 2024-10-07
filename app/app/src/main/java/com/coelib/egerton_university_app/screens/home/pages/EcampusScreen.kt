package com.coelib.egerton_university_app.screens.home.pages

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcampusScreen(navigateBack: () -> Unit){
  Scaffold(
      topBar = {
          TopAppBar(
              title = { /*TODO*/
                  Text(text = "E-Campus")
              },
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
  ) { paddingValues ->
      Box(
          modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues)

      ){
          WebView()
      }
  }
}

@Composable
fun WebView(){

    // Declare a string that contains a url
    val mUrl = "https://ecampus.egerton.ac.ke/"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        android.webkit.WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webViewClient = CustomWebViewClient()
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}

class CustomWebViewClient: WebViewClient(){
    @Deprecated("Deprecated in Java",
        ReplaceWith("url != null && url.startsWith(\"https://ecampus.egerton.ac.ke/\")")
    )
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return url != null && url.startsWith("https://ecampus.egerton.ac.ke/")
    }
}