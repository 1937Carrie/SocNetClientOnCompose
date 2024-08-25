package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.models.MyContactsContact

data class MyContactsState(
    val contacts: List<MyContactsContact> = emptyList(),
    val isMultiselect: Boolean = false,
    val searchState: Boolean = false,
    val searchQuery: String = "",
)