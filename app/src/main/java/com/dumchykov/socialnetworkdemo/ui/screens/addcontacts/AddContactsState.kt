package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import com.dumchykov.socialnetworkdemo.ui.screens.addcontacts.data.AddContactsContact

data class AddContactsState(
    val allContacts: List<AddContactsContact> = emptyList(),
    val searchState: Boolean = false,
    val searchQuery: String = "",
)