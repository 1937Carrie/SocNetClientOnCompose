package com.dumchykov.socialnetworkdemo.ui.screens.detail

import com.dumchykov.socialnetworkdemo.util.BaseContact
import com.dumchykov.socialnetworkdemo.util.BaseContactImpl

data class DetailState(
    val contact: BaseContact = BaseContactImpl(),
    val inFriendList: Boolean = false,
)
