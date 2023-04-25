package com.stanislavdumchykov.socialnetworkclient.domain.utils

import androidx.annotation.StringRes
import com.stanislavdumchykov.socialnetworkclient.R

enum class NavigationRoutes(@StringRes val title: Int) {
    SignUp(R.string.route_signup),
    Pager(R.string.route_pager),
    ContactProfile(R.string.route_contact_profile)
}