package com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.contactlist.ContactList
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.myprofile.MyProfile
import com.stanislavdumchykov.socialnetworkclient.domain.utils.ScreenList

// For supporting code purpose https://developer.android.com/jetpack/compose/layouts/pager
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pages(contactListOnItemClick: (String, String, String) -> Unit, email: String) {
    val pagerState = rememberPagerState()

    HorizontalPager(pageCount = ScreenList.values().size, state = pagerState) { page ->
        when (page) {
            ScreenList.MY_PROFILE.ordinal -> {
                MyProfile(pagerState, email = email)
            }
            ScreenList.CONTACT_LIST.ordinal -> {
                ContactList(contactListOnItemClick, pagerState)
            }
        }
    }
}