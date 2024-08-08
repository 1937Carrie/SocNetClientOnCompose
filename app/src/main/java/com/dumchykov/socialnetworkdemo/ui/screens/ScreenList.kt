package com.dumchykov.socialnetworkdemo.ui.screens

import kotlinx.serialization.Serializable

@Serializable
data class MyProfile(val email: String)

@Serializable
object SignUp