package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import com.dumchykov.socialnetworkdemo.ui.screens.myprofile.data.MyProfileContact

data class MyProfileState(
    val user: MyProfileContact = MyProfileContact(),
    val credentialsIsCleared: Boolean = false,
)
