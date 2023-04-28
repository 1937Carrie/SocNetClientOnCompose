package com.stanislavdumchykov.socialnetworkclient.data.repository

import com.stanislavdumchykov.socialnetworkclient.data.UserList
import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val inMemoryDb: UserList
) : UsersRepository {
    override suspend fun getUsers(): List<LocalUser> = inMemoryDb.localUsers
}