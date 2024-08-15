package com.dumchykov.socialnetworkdemo.ui.screens.pager

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.MyContactsScreen
import com.dumchykov.socialnetworkdemo.ui.screens.myprofile.MyProfileScreen

@Composable
fun PagerScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { 2 }
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> {
                MyProfileScreen(padding, navController, pagerState)
            }

            1 -> {
                MyContactsScreen(padding, navController, pagerState)
            }
        }
    }
}
