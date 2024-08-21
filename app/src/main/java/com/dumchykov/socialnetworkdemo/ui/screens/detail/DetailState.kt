package com.dumchykov.socialnetworkdemo.ui.screens.detail

import com.dumchykov.contactsprovider.domain.BaseContact
import com.dumchykov.contactsprovider.domain.Contact

data class DetailState(
    val contact: BaseContact = Contact(),
    val inFriendList: Boolean = false,
)
