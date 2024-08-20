package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import com.dumchykov.contactsprovider.domain.Contact

data class MyProfileState(
    val user: Contact = Contact(),
    val credentialsIsCleared: Boolean = false,
)
