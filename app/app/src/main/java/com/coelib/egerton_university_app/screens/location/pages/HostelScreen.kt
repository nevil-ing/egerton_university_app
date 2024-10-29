package com.coelib.egerton_university_app.screens.location.pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.R
import com.coelib.egerton_university_app.screens.location.model.ItemListModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostelScreen(navigateBack: () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hostels") },
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
            HostelBody(paddingValues)

    }
}

@Composable
fun HostelBody(paddingValues: PaddingValues){
    val context = LocalContext.current
    val itemListModal = getHostelData()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemListModal){item ->
            HostelCard(item, context)
        }
    }
}
@Composable
fun HostelCard(item: ItemListModal, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.itemImage),
                contentDescription = item.itemTitle,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.itemTitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = item.itemDescription,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                openNavigation(context, item.latitude, item.longitude)
            }) {
                Text("Directions")
            }
        }
    }
}
private fun openNavigation(context: Context, latitude: Double, longitude: Double) {
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=Egerton University")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps") // crucial for reliability
    try {
        context.startActivity(mapIntent)
    } catch (e: android.content.ActivityNotFoundException) {
        // Handle the case where the Google Maps app isn't installed
        Toast.makeText(context,"Google Maps app not installed.", Toast.LENGTH_SHORT).show()
    }
}
fun getHostelData() = listOf(
    ItemListModal(
        "Barret hostel",
        R.drawable.egertonintroduction,"",
        -0.275, 35.291),
    ItemListModal(
        "Buruburu hostels",
        R.drawable.egertonintroduction,
        "" ,
        -0.275, 35.291),
    ItemListModal(
        "hollywood hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Mama Ngina hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Maringo hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "New hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Riverside hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Riverview hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Taifa hostels",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
    ItemListModal(
        "Uganda hostel",
        R.drawable.egertonintroduction,
        "",
        -0.275, 35.291),
)