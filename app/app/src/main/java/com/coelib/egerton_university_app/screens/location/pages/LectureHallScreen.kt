package com.coelib.egerton_university_app.screens.location.pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
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
fun LectureHallScreen(navigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lecture Rooms") },
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
        LectureHallBody(paddingValues)
    }
}

@Composable
fun LectureHallBody(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val itemListModal = getLectureHallData() // Load data efficiently

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemListModal) { item ->
            LectureHallCard(item, context)
        }
    }
}


@Composable
fun LectureHallCard(item: ItemListModal, context: Context) {
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

// Helper function to prevent redundant data loading.
fun getLectureHallData() = listOf(
    ItemListModal(
        "A5 Lecture room",
        R.drawable.egertonintroduction,
        "Situated in Department of Animal Science(small room",
        -0.275, 35.291),
    ItemListModal(
        "B Blocks",
        R.drawable.egertonintroduction,
        "Bs are next to Faculty of Arts and Social Sciences (FASS).\n" +
                "Lecture rooms: B1,B2,B3,B4,B5." ,
        -0.275, 35.291),
    ItemListModal(
        "Department of Agronomy",
        R.drawable.egertonintroduction,
        "Lecture room: A1",
        -0.275, 35.291),
    ItemListModal(
        "Education complex (FEDCOS)",
        R.drawable.egertonintroduction,
        "Lecture rooms: Ground Floor(GO14A, GO14, GO15)\n" +
                "First Floor(ED15,ED13,ED14,ED15,ED16)\n" +
                "Second Floor(ED20,ED23,ED24,ED25,ED26,ED27,ED28,ED29)\n" +
                "Basement(EB1,EB2,EB3,EB4,EB5,EB6,EB7,EB8",
        -0.275, 35.291),
    ItemListModal(
        "Engineering labs",
        R.drawable.egertonintroduction,
        "Engineering labs are next to B Blocks.\n" +
                "Lecture rooms: EL1,EL2",
        -0.275, 35.291),
    ItemListModal(
        "F2 Lecture room",
        R.drawable.egertonintroduction,
        "Located in Department of Dairy Science(2)",
        -0.275, 35.291),
    ItemListModal(
        "FASS Theatre halls",
        R.drawable.egertonintroduction,
        "Lecture rooms: FT1,FT2",
        -0.275, 35.291),
    ItemListModal(
        "Holland hall",
        R.drawable.egertonintroduction,
        "Situated behind Department of Computer Science.\n" +
                "Lecture rooms: H1,H2,H3",
        -0.275, 35.291),
    ItemListModal(
        "Kennedy hall (JF)",
        R.drawable.egertonintroduction,
        "Lecture room: KEN DH",
        -0.275, 35.291),
    ItemListModal(
        "Kilimo hall",
        R.drawable.egertonintroduction,
        "Lecture rooms: K,KS1,KS2",
        -0.275, 35.291),
    ItemListModal(
        "New Dining hall",
        R.drawable.egertonintroduction,
        "Lecture rooms: NEW DH",
        -0.275, 35.291),
    ItemListModal(
        "Physical Science Complex(New halls)",
        R.drawable.egertonintroduction,
        "Lecture rooms: NPL A, NPL B, NPL 2A, NPL 2B, NPL 3A, NPL 3B, NPL 4A, NPL 4B",
        -0.275, 35.291),
    ItemListModal(
        "Science Complex Chemistry Block B",
        R.drawable.egertonintroduction,
        "Situated near Department of Dairy.\n" +
                "Lecture rooms: CB1, CB2, CB3",
        -0.275, 35.291),
    ItemListModal(
        "Science Complex Physics Block",
        R.drawable.egertonintroduction,
        "Situated near Department of Dairy." +
                "Lecture rooms: PL1, PL2, PL3, PL4, PL5, PL6, PL7, PL8, PL9, PL10, PL11, PL12",
        -0.275, 35.291),
    ItemListModal(
        "T Blocks",
        R.drawable.egertonintroduction,
        "Ts are next to Timetabling department." +
                "Lecture roooms: T1,T2.",
        -0.275, 35.291),
    ItemListModal(
        "Upper mess",
        R.drawable.egertonintroduction,
        "Situated near the laundry-kiboko wing",
        -0.275, 35.291),

    )

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