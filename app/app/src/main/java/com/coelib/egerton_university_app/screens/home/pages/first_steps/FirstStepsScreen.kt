package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.layout.Column // Changed Row to Column for better content layout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager // Specify horizontal pager for clarity
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FirstStepsScreen() {
    val pagerState = rememberPagerState(pageCount = { 6 })  // Remember initial page count

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {page ->
        when (page) {
            0 -> WelcomeBoard()
            1 -> LocationBoard()
            2 -> RegistrationBoard()
            3 -> IntroductoryBoard()
            4 -> LibraryBoard()
            5 -> SecurityBoard()
        }
    }
}