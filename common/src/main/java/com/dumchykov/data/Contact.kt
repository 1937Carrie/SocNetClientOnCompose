package com.dumchykov.data

import java.util.Calendar

data class Contact(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val career: String = "",
    val address: String = "",
    val birthday: Calendar? = null,
    val facebook: String = "",
    val instagram: String = "",
    val twitter: String = "",
    val linkedin: String = "",
    val image: String? = null,
    val createdAt: Calendar = Calendar.getInstance(),
    val updatedAt: Calendar = Calendar.getInstance(),
)