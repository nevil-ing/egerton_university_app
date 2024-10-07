package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun LibraryBoard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LibraryYouTubeScreen("JoR2S45fcPU")
        Text(
            text= "Egerton School library",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Currently Egerton University Library system has 9 branches namely Main Library, Faculty of Arts and Social Sciences (FASS)," +
                    "Faculty of Education(FEDCOS), J.D Rockefeller Library (TEEAL), Nakuru Town Campus College Library(NTCCL), Nakuru Town Centre Library, Faculty of Health Science(FHS)," +
                    "Law School Library and Nairobi City Campus Library (NCCL)." +
                    "The University Library has subscription of e-books and e-journals that are accessible to all through the library and also via EZ proxy for off campus access." +
                    "Membership is open to full time registerd students, academic stuff, administrative staff and all other non-academic staff." +
                    " Other persons may be given membership for the purpose of consultation to our information resource at a fee.",

            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }
}
//JoR2S45fcPU

@Composable
fun LibraryYouTubeScreen(videoId: String){
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
