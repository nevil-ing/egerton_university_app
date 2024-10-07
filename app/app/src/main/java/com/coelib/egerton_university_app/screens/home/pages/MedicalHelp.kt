package com.coelib.egerton_university_app.screens.home.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalHelp(navigateBack: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Medical Help") },
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
                .padding(16.dp)
        ) {
            Column {
                ExpandableListItem(
                    title = "Health Care",
                    content = "Egerton University medical department" +
                            "The University health services are provided by Medical department is a service unit. The department" +
                            "compliments other services offered by other departments within the University. Its mission is to " +
                            "maintain a healthy environment through offering curative, promotive, preventive and rehabilitative health services to staff," +
                            "students and members of the public \n \n" +
                            "The facility is manned by qualified staff in the areas of clinical, nursing, public health, pharmaceutical, dental, labaratory," +
                            "medical records, administration and other specialized services. Services available to students include consultation, dispensing and purchase of drugs, basic labaratory " +
                            "investigations, basic x-ray, and hospitalization."
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableListItem(
                    title = "Clinical Days",
                    content = "Mondays: Pediatric\nTuesdays: Tuberculosis\nWednesdays: Obstetrics/Gynecology" +
                        "\nThursdays: Surgical\nMondays/Fridays: Dental")
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableListItem(
                    title = "Pharmacy",
                    content = "Egerton University medical department has a well stocked pharmacy." +
                            " Drugs are only given as prescribed by the doctor within the University")


                Button(
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


                ) {
                    Icon(
                        imageVector = Icons.Filled.Call, // Call icon from Material Icons
                        contentDescription = "Call Security"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text ="MEDICAL EMERGENCY CALL"
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableListItem(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Expand/Collapse"
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
