package com.dumchykov.socialnetworkdemo.ui.screens.editprofile

import java.util.Calendar

data class EditProfileState(
    val name: String = "",
    val career: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val dateOfBirth: Calendar = Calendar.getInstance(),
)
