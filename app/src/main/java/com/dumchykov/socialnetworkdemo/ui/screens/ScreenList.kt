package com.dumchykov.socialnetworkdemo.ui.screens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyProfile(val email: String)

@Serializable
data object SignUp

@Serializable
data object MyContacts

@Serializable
data class Detail(@SerialName("id") val contactId: Int)

@Serializable
data object Pager

@Serializable
data object LogIn

@Serializable
data object SignUpExtended

@Serializable
data object AddContacts

@Serializable
data object EditProfile