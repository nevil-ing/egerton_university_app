package com.coelib.egerton_university_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.coelib.egerton_university_app.screens.App
import com.coelib.egerton_university_app.ui.theme.Egerton_university_appTheme
import com.coelib.egerton_university_app.utils.networkUtils.ConnectivityObserver
import com.coelib.egerton_university_app.utils.networkUtils.NetworkConnectivityObserver
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import androidx.work.*
import com.coelib.egerton_university_app.utils.workers.NewsWorker
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver
    private val TAG = "MainActivity"

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(3000)
            keepSplashScreen = false
        }
        // Initialize Firebase
        FirebaseApp.initializeApp(this)


       // schedulePeriodicNotificationWorker()
        //scheduleNewsWorker()


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
                        hostState = snackbarHostState
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
/*
    private fun schedulePeriodicNotificationWorker() {
        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(15, TimeUnit.MINUTES) // initial delay before the first run
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "NotificationWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            notificationRequest
        )
    }
    private fun scheduleNewsWorker() {
        val workRequest = PeriodicWorkRequestBuilder<NewsWorker>(12, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "NewsWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }


 */
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
