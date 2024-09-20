package com.coelib.egerton_university_app.screens.home.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EcampusScreen(){
   Row(
       modifier = Modifier
           .fillMaxSize()

   ) {
       Text(text = "hello Campus")
   }
}