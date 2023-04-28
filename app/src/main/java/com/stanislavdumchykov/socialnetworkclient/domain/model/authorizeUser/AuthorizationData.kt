package com.stanislavdumchykov.socialnetworkclient.domain.model.authorizeUser

import com.stanislavdumchykov.socialnetworkclient.domain.model.User

data class AuthorizationData(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)