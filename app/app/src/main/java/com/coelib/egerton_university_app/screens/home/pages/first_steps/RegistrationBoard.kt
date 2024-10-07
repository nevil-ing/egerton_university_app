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
fun RegistrationBoard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.egertonregistration),
            contentScale = ContentScale.Crop,  // Scales and crops the image to fill the width
            modifier = Modifier
                .fillMaxWidth() // Make the image fill the entire width
                .height(200.dp),  // Adjust height as needed
            contentDescription = "icon"
        )
        Text(
            text= "University Registration",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "To be able to follow classes and make use of facilities at Egerton University, such as the school library and other recreational facilities" +
                    "you need to be enrolled as a student. First, you need to process your student smart card that will serve as an identification document." +
                    "Follow the steps enlisted in the first steps category, on how to acquire a smart card " +
                    "To be able to borrow books from the library, you have to register first with the school librarian",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }
}