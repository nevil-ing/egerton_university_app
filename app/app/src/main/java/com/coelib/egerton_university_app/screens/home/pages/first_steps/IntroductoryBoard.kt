package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.R

@Composable
fun IntroductoryBoard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.egertonintroduction),
            contentScale = ContentScale.Crop,  // Scales and crops the image to fill the width
            modifier = Modifier
                .fillMaxWidth() // Make the image fill the entire width
                .height(200.dp),  // Adjust height as needed
            contentDescription = "icon"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text= "Introductory activities",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "At the beginning of an academic year, we welcome new students through an Orientation program that lasts a week" +
                    "Throughout this week, new students are guided and given tours within the university. " +
                    "Important information is usually also passed onto new students by Egerton family fraternity.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }
}