package com.coelib.egerton_university_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.coelib.egerton_university_app.screens.App
import com.coelib.egerton_university_app.ui.theme.Egerton_university_appTheme
import com.google.firebase.Firebase
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

    }

    private fun retrieveFCMToken() {
        Firebase.messaging.subscribeToTopic("notify")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }
}