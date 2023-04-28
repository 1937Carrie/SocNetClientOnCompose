package com.stanislavdumchykov.socialnetworkclient.domain.model.register

import com.stanislavdumchykov.socialnetworkclient.domain.model.User

data class RegisterData(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)