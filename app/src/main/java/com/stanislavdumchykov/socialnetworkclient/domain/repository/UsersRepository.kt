package com.stanislavdumchykov.socialnetworkclient.domain.repository

import com.stanislavdumchykov.socialnetworkclient.domain.model.User

interface UsersRepository {
    suspend fun getHardcodedUsers(): List<User>
}