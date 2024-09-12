package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.data.MyContactsIndicatorContact
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState

data class MyContactsState(
    val contacts: List<MyContactsIndicatorContact> = emptyList(),
    val responseState: ResponseState = ResponseState.Initial,
    val isDatabaseGottenOnStart: Boolean = false,
    val isMultiselect: Boolean = false,
    val searchState: Boolean = false,
    val searchQuery: String = "",
)