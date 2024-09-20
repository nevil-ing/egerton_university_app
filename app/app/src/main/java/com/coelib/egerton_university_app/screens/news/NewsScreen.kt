package com.coelib.egerton_university_app.screens.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.screens.news.tabviews.NewsTab
import com.coelib.egerton_university_app.screens.news.tabviews.NoticeTab
import kotlinx.coroutines.launch


@Composable
fun NewsScreen(){
    Scaffold(
        topBar = { CusTopBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    val pageState = rememberPagerState(
                        pageCount = {2}
                    )
                    val coroutineScope = rememberCoroutineScope()
                    TabRow(
                        selectedTabIndex =pageState.currentPage,
                        divider = {},
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[pageState.currentPage]),

                            )
                        }

                    ) {
                        Tab(
                            selected = pageState.currentPage==0,
                            text = {
                                   Text(text = "News")
                            },
                            onClick = { /*TODO*/
                            coroutineScope.launch {
                                pageState.scrollToPage(0)
                            }}
                        )
                        Tab(
                            selected = pageState.currentPage==1,
                            text = {
                                Text(
                                    text = "Notices"
                                )
                            },
                            onClick = { /*TODO*/
                                coroutineScope.launch {
                                    pageState.animateScrollToPage(1)
                                }
                            }
                        )
                    }
                    HorizontalPager(
                        state = pageState,
                        userScrollEnabled = false,

                    ) {page ->
                        when (page) {
                            0 -> NewsTab()
                            1 -> NoticeTab() 
                            
                        }
                }
            }
        }
        }
    )
}

@Composable
fun CusTopBar() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Egerton News & Notices",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

