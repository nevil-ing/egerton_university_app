package com.coelib.egerton_university_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.coelib.egerton_university_app.screens.App
import com.coelib.egerton_university_app.ui.theme.Egerton_university_appTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Top-level dark theme state
        var isDarkTheme by mutableStateOf(false)

        setContent {
            Egerton_university_appTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    retrieveFCMToken()
                    App(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })
                }
            }
        }

        // Handle notification data if the activity was started from a notification
        handleIntentData()
    }

    private fun retrieveFCMToken() {
        FirebaseMessaging.getInstance().subscribeToTopic("notify")
            .addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) {
                    "Subscribed to notifications"
                } else {
                    "Subscription failed"
                }
                Log.d(TAG, msg)
               // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleIntentData() {
        intent?.extras?.let { bundle ->
            // Handle the data passed from the notification
            val messageId = bundle.getString("message_id")
            if (messageId != null) {
                // TODO: Handle the notification message ID
                Log.d(TAG, "Received message ID: $messageId")
            }
        }
    }
}
