package com.coelib.egerton_university_app.screens.home.pages.first_steps

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.R

@Composable
fun SecurityBoard(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.egertonsecurity),
            contentScale = ContentScale.Crop,  // Scales and crops the image to fill the width
            modifier = Modifier
                .fillMaxWidth() // Make the image fill the entire width
                .height(200.dp),  // Adjust height as needed
            contentDescription = "icon"
        )
        Text(
            text= "University Security",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "The Security Department is charged with the responsibility of providing security services to the university." +
                    " It is headed by the Chief Security Officer who is assisted by supervisors and other security staff." +
                    "The duties of the department include: Protection of university properties, staff and students within the campus; invistigating cases" +
                    "reported to the Department e.g. thefts/losses, assaults, missing students, gender conflicts, reports on suspicious elements, etc." +
                    "The department also liaises with other departments on matters other than security affecting the comfort of students within the university." +
                    "Students making reports are advised to consult with the Chief Security Officer or any supervisor on duty whenever they are not satisfied with the services provided at the department." +
                    "Use mobile phone number 0725964695 to report emergency cases. The security office appeals to all students to accept security " +
                    "staff as friendly and volunteer any information for their own safety. " ,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Call for security emergencies by clicking the call button above",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        FloatingActionButton(
            onClick = {
                // Intent to open the phone dialer with the specified number
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:0725964695")
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Filled.Call, // Call icon from Material Icons
                contentDescription = "Call Security"
            )
        }
    }
}