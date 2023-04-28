package com.stanislavdumchykov.socialnetworkclient.domain.model.contacts

import com.stanislavdumchykov.socialnetworkclient.domain.model.User

data class Contacts(
    val contacts: List<User>
)