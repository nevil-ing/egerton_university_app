package com.coelib.egerton_university_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.coelib.egerton_university_app.screens.App
import com.coelib.egerton_university_app.ui.theme.Egerton_university_appTheme
import com.coelib.egerton_university_app.utils.networkUtils.ConnectivityObserver
import com.coelib.egerton_university_app.utils.networkUtils.NetworkConnectivityObserver
import com.coelib.egerton_university_app.utils.workers.scheduleNotificationWorker // Import the function here
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.coelib.egerton_university_app.utils.workers.ApiRequestWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver
    private val TAG = "MainActivity"

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Schedule the notification worker
        scheduleNotificationWorker(applicationContext)
        scheduleApiRequestWorker(applicationContext)

        // Dark theme toggle
        var isDarkTheme by mutableStateOf(false)
        connectivityObserver = NetworkConnectivityObserver(application = application)

        setContent {
            Egerton_university_appTheme(darkTheme = isDarkTheme) {
                val status by connectivityObserver.observe().collectAsState(initial = connectivityObserver.isConnected())
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    retrieveFCMToken()
                    App(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })

                    LaunchedEffect(status) {
                        val (message, color) = when (status) {
                            ConnectivityObserver.Status.Available -> "Internet Connected" to Color(0xFF4CAF50) // Green
                            ConnectivityObserver.Status.Unavailable,
                            ConnectivityObserver.Status.Lost -> "No Internet Connection" to Color(0xFFF44336) // Red
                            ConnectivityObserver.Status.Losing -> "Connection Losing" to Color(0xFFFFA726) // Orange
                        }

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                withDismissAction = true
                            )
                        }
                    }

                    SnackbarHost(
                        hostState = snackbarHostState,

                        ) { data ->
                        androidx.compose.material3.Snackbar(
                            snackbarData = data,
                            containerColor = when (status) {
                                ConnectivityObserver.Status.Available -> Color(0xFF4CAF50) // Green
                                ConnectivityObserver.Status.Unavailable,
                                ConnectivityObserver.Status.Lost -> Color(0xFFF44336) // Red
                                ConnectivityObserver.Status.Losing -> Color(0xFFFFA726) // Orange
                            }
                        )
                    }
                }
            }
        }
        handleIntentData()
    }

    private fun scheduleApiRequestWorker(context: Context) {
        val apiRequestWork = PeriodicWorkRequestBuilder<ApiRequestWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ApiRequestWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            apiRequestWork
        )
    }

    private fun retrieveFCMToken() {
        FirebaseMessaging.getInstance().subscribeToTopic("notify")
            .addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) "Subscribed to notifications" else "Subscription failed"
                Log.d(TAG, msg)
            }
    }
    private fun handleIntentData() {
        intent?.extras?.let { bundle ->
            val messageId = bundle.getString("message_id")
            if (messageId != null) {
                Log.d(TAG, "Received message ID: $messageId")
            }
        }
    }
}
