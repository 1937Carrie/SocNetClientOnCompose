package com.dumchykov.socialnetworkdemo.ui.screens.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.MyContactsScreen
import com.dumchykov.socialnetworkdemo.ui.screens.myprofile.MyProfileScreen
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun PagerScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val tabItems = listOf(
        TabItem(stringResource(R.string.my_profile)),
        TabItem(stringResource(R.string.my_contacts))
    )
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.background(Blue)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = padding.calculateTopPadding()),
            containerColor = Blue,
            contentColor = White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = White
                )
            },
            divider = {}
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        coroutineScope.launch {
                            selectedTabIndex = index
                            pagerState.scrollToPage(index)
                        }
                    }
                ) {
                    Text(
                        text = tabItem.title,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    MyProfileScreen(
                        padding = padding,
                        navController = navController,
                        onViewMyContactsClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(1)
                            }
                        })
                }

                1 -> {
                    MyContactsScreen(
                        padding = padding,
                        navController = navController,
                        onNavigationArrowClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(0)
                            }
                        }
                    )
                }
            }
        }
    }
}