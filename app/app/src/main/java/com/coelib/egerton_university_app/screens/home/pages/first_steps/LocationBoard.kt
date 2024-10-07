package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.R

@Composable
fun LocationBoard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.egertontransport),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentDescription = "icon"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "How to get to Egerton University",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Egerton University has its main campus in Njoro, which is 23 kilometres from Nakuru town." +
                    " From Nakuru town, you board a matatu at Njoro matatu stage that is located next to java house building, " +
                    "rear side of Naivas supermarket. You'll be charged kshs.70 as transport fare." +
                    " It will take the matatu an average of 30 minutes to reach Egerton University.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }
}
