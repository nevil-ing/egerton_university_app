package com.coelib.egerton_university_app.screens.home.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.coelib.egerton_university_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortalScreen(navigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Portal") },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PortalBody()
        }
    }
}

@Composable
fun PortalBody() {
    val context = LocalContext.current

    val portals = listOf(
        PortalItem("Student Portal", "https://studentportal.egerton.ac.ke/portal/"),
        PortalItem("Staff Portal", "https://staffportal.egerton.ac.ke/"),
        PortalItem("Parent Portal", "https://parents.egerton.ac.ke/")
    )

    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(portals.size) { index ->
            PortalCard(
                portal = portals[index],
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(portals[index].url))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortalCard(portal: PortalItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Use painterResource if you have an icon for each portal
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder for portal icons
                contentDescription = "${portal.title} icon"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = portal.title,
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class PortalItem(val title: String, val url: String)
