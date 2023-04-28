package com.stanislavdumchykov.socialnetworkclient.data

import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
import javax.inject.Inject

class UserList @Inject constructor() {
    private val name = "Player"
    private val job = "Job"

    val localUsers = mutableListOf(
        LocalUser(
            id = 0,
            name = "$name 1",
            career = "$job 1"
        ),
        LocalUser(
            id = 1,
            name = "$name 2",
            career = "$job 2"
        ),
        LocalUser(
            id = 2,
            name = "$name 3",
            career = "$job 3"
        ),
        LocalUser(
            id = 3,
            name = "$name 4",
            career = "$job 4"
        ),
        LocalUser(
            id = 4,
            name = "$name 5",
            career = "$job 5"
        ),
        LocalUser(
            id = 5,
            name = "$name 6",
            career = "$job 6"
        ),
        LocalUser(
            id = 6,
            name = "$name 7",
            career = "$job 7"
        ),
        LocalUser(
            id = 7,
            name = "$name 8",
            career = "$job 8"
        ),
        LocalUser(
            id = 8,
            name = "$name 9",
            career = "$job 9"
        ),
        LocalUser(
            id = 9,
            name = "$name 10",
            career = "$job 10"
        ),
        LocalUser(
            id = 10,
            name = "$name 11",
            career = "$job 11"
        ),
        LocalUser(
            id = 11,
            name = "$name 12",
            career = "$job 12"
        ),
        LocalUser(
            id = 12,
            name = "$name 13",
            career = "$job 13"
        ),
        LocalUser(
            id = 13,
            name = "$name 14",
            career = "$job 14"
        ),
    )
}