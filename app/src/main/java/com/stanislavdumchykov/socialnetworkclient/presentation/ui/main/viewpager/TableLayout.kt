package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.stanislavdumchykov.socialnetworkclient.presentation.SharedViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.contactlist.ContactList
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.myprofile.MyProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.ScreenList

// For supporting code purpose https://developer.android.com/jetpack/compose/layouts/pager
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pages(
    onEditProfileClick: () -> Unit,
    contactListOnItemClick: (String, String, String) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val pagerState = rememberPagerState()

    HorizontalPager(pageCount = ScreenList.values().size, state = pagerState) { page ->
        when (page) {
            ScreenList.MY_PROFILE.ordinal -> {
                MyProfile(pagerState, sharedViewModel, onEditProfileClick)
            }

            ScreenList.CONTACT_LIST.ordinal -> {
                ContactList(contactListOnItemClick, pagerState)
            }
        }
    }
}