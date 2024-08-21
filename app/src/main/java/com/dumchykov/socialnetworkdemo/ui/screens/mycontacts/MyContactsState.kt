package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import com.dumchykov.contactsprovider.domain.Contact

data class MyContactsState(
    val contacts: List<Contact> = emptyList(),
    val isMultiselect: Boolean = false,
    val searchState: Boolean = false,
    val searchQuery: String = "",
)
