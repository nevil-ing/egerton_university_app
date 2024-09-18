package com.coelib.egerton_university_app.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.routes.Routes
import com.coelib.egerton_university_app.screens.home.model.GridModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

  Scaffold(
    topBar = { CustomTopBar() },
    content = { paddingValues ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
        Column {
          Spacer(modifier = Modifier.height(15.dp))
          GridView(navController)
          Spacer(modifier = Modifier.height(20.dp))
          RecentNews()
        }
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    //horizontalArrangement = Arrangement.SpaceBetween // Space between elements
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
     .weight(1f)// Align items in this row
    ) {
      // Egerton University logo
      Image(
        painter = painterResource(id = R.drawable.egertonuniversity_logo),
        contentDescription = "Egerton logo",
        contentScale = ContentScale.Fit, // Scale logo to fit within the size
        modifier = Modifier.size(54.dp) // Set logo size
      )

      // Column for university name and tagline
      Column(
        modifier = Modifier.padding(start = 5.dp) // Add padding between logo and text
      ) {
        Text(
          text = "EGERTON UNIVERSITY",
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.titleLarge
        )
        Text(
          text = "Transforming Lives Through Quality Education",
          style = MaterialTheme.typography.titleMedium
        )
      }
    }

    IconButton(onClick = { /* Handle settings click */ }) {
      Icon(
        imageVector = Icons.Filled.Settings,
        contentDescription = "Settings"
      )
    }
  }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridView(navController: NavController){
  val itemList = listOf(
    GridModal("First steps", R.drawable.firststeps, Routes.FirstStep.routes),
    GridModal("Medical help", R.drawable.medical, Routes.MedicalHelp.routes),
    GridModal("E-campus", R.drawable.ecampus, Routes.Ecampus.routes),
    GridModal("Portal", R.drawable.portal, Routes.Portal.routes)
  )

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = Modifier.padding(10.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(itemList.size) { index ->
      Card(
        onClick = { /* Handle click */
          Log.d("Navigation", "Route: ${itemList[index].route}")
          navController.navigate(itemList[index].route)
                  },
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
      ) {
        Column(
          Modifier
            .fillMaxSize()
            .padding(12.dp), // Increase padding for better spacing
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Image(painter = painterResource(id = itemList[index].image),
            contentDescription = "icon")
          Spacer(modifier = Modifier.height(9.dp))
          Text(
            text = itemList[index].title,
            modifier = Modifier.padding(4.dp),
            color = Color.Black,
            textAlign = TextAlign.Center // Center the text
          )
        }
      }
    }
  }
}
//show recent news
@Composable
fun RecentNews(){
  Row (
    modifier = Modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ){
    Text(
      text = "Recent News",
      modifier = Modifier
        .padding(10.dp)
    )

    Text(
      text = "See all",
      modifier = Modifier
        .padding(10.dp)
    )
  }
}