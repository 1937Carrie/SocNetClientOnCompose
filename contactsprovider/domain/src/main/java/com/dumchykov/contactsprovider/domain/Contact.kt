package com.dumchykov.contactsprovider.domain

import java.io.Serializable

data class Contact(
    val id: Int = 0,
    val name: String = "",
    val profession: String = "",
    val address: String = "",
    val imageHolder: Int,
    val imageInternet: String = "",
): Serializable
