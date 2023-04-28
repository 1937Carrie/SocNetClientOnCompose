package com.stanislavdumchykov.socialnetworkclient.domain.repository

import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser

interface UsersRepository {
    suspend fun getUsers(): List<LocalUser>
}