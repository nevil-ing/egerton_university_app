package com.coelib.egerton_university_app.screens.home.pages.first_steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coelib.egerton_university_app.components.bottombar.BottomNavigationItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstStepsScreen(navigateBack: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 6 })  // Initialize the page count
    val coroutineScope = rememberCoroutineScope()  // Used for launching coroutines for page scrolling

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "First Steps") },
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

        // Box to manage the layout of the content, dots, and navigation button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // HorizontalPager to display different onboarding pages
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                // Your onboarding pages, e.g.
                when (page) {
                    0 -> WelcomeBoard()
                    1 -> LocationBoard()
                    2 -> RegistrationBoard()
                    3 -> IntroductoryBoard()
                    4 -> LibraryBoard()
                    5 -> SecurityBoard()
                }
            }

            // Row for Dots Indicator and Next Button
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Page Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                    repeat(6) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .padding(horizontal = 4.dp)
                                .background(
                                    color = if (isSelected) MaterialTheme.colorScheme.inversePrimary else Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }
                }


                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pagerState.pageCount - 1) {
                                // Scroll to the next page if not on the last one
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            } else {
                                navigateBack
                            }
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = if (pagerState.currentPage < pagerState.pageCount - 1) "Next" else "Finish"
                    )
                }
            }
        }
    }
}
