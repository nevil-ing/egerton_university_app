package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LibraryBoard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Egerton School library",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Currently Egerton University Library system has 9 branches namely Main Library, Faculty of Arts and Social Sciences (FASS)," +
                    "Faculty of Education(FEDCOS), J.D Rockefeller Library (TEEAL), Nakuru Town Campus College Library(NTCCL), Nakuru Town Centre Library, Faculty of Health Science(FHS)," +
                    "Law School Library and Nairobi City Campus Library (NCCL)." +
                    "The University Library has subscription of e-books and e-journals that are accessible to all through the library and also via EZ proxy for off campus access." +
                    "Membership is open to full time registerd students, academic stuff, administrative staff and all other non-academic staff." +
                    " Other persons may be given membership for the purpose of consultation to our information resource at a fee.",

            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}