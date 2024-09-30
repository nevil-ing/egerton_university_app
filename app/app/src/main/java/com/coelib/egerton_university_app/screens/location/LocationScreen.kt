package com.coelib.egerton_university_app.screens.location

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.components.bottombar.BottomNavigationItems.HomeScreen.title
import com.coelib.egerton_university_app.routes.Routes
import com.coelib.egerton_university_app.screens.home.CustomTopBar
import com.coelib.egerton_university_app.screens.home.GridView
import com.coelib.egerton_university_app.screens.home.RecentNews
import com.coelib.egerton_university_app.screens.location.model.ListModal
import okhttp3.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LocationScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                title = { /*TODO*/
                    Text(
                        text = "Places"

                    )


                }

            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Choose a place you want to be directed",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LocationBody(navController)
                }
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationBody(navController: NavController){
    val itemList = listOf(
        ListModal("Offices", R.drawable.offices, Routes.Office.routes ),
        ListModal("LectureHalls",R.drawable.lecture, Routes.Lecture.routes),
        ListModal("Cafeteria",R.drawable.cafeteria, Routes.Cafeteria.routes),
        ListModal("Hostels", R.drawable.hostels,Routes.Hostel.routes)
    )
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemList.size){ index ->
            Card(onClick = { /*TODO*/
                Log.d("Navigation", "Route: ${itemList[index].route}")
                navController.navigate(itemList[index].route)
            }) {
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
