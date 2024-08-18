package com.dumchykov.socialnetworkdemo.ui.screens

import com.dumchykov.contactsprovider.domain.Contact
import kotlinx.serialization.Serializable

@Serializable
data class MyProfile(val email: String)

@Serializable
data object SignUp

@Serializable
data object MyContacts

@Serializable
data class Detail(val contact: Contact)

@Serializable
data class Pager(val email: String)

@Serializable
data object LogIn