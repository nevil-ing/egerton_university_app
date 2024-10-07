package com.coelib.egerton_university_app.screens.home.pages.first_steps

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun WelcomeBoard() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),

  ) {
    // Correct YouTube video ID without extra parameters
    YouTubeScreen("HL0VzGmSDGE")
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Welcome to Egerton University",
      style = MaterialTheme.typography.headlineLarge,
      fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Welcome to the Egerton University, and congratulations on making it to this great institution of higher learning.",
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.SemiBold,
      color = Color.Gray,

    )
  }
}

@Composable
fun YouTubeScreen(videoId: String) {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  var youTubePlayer by remember { mutableStateOf<YouTubePlayer?>(null) }

  // Create the YouTubePlayerView once and remember it
  val youTubePlayerView = remember {
    YouTubePlayerView(context)
  }

  // Manage the lifecycle and YouTube player setup
  DisposableEffect(lifecycleOwner, youTubePlayerView) {
    val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
      override fun onReady(player: YouTubePlayer) {
        youTubePlayer = player
        player.loadVideo(videoId, 0f)
      }
    }

    youTubePlayerView.addYouTubePlayerListener(youTubePlayerListener)

    // Manage lifecycle events
    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        Lifecycle.Event.ON_PAUSE -> youTubePlayer?.pause()
        Lifecycle.Event.ON_RESUME -> youTubePlayer?.play()
        Lifecycle.Event.ON_DESTROY -> youTubePlayerView.release()
        else -> Unit
      }
    }

    lifecycleOwner.lifecycle.addObserver(observer)

    // Clean up when leaving the composition
    onDispose {
      youTubePlayerView.removeYouTubePlayerListener(youTubePlayerListener)  // Properly remove the listener
      lifecycleOwner.lifecycle.removeObserver(observer)
      youTubePlayerView.release()
    }
  }

  // Display the YouTubePlayerView using AndroidView
  AndroidView(
    factory = { youTubePlayerView },
    modifier = Modifier
      .fillMaxWidth()
      .height(250.dp)
  )
}
