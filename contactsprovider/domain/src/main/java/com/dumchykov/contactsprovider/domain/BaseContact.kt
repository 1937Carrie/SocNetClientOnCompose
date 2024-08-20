package com.dumchykov.contactsprovider.domain

import com.dumchykov.socialnetworkdemo.webapi.domain.models.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

interface BaseContact {
    @SerialName("id")
    val id: Int
    @SerialName("name")
    val name: String?
    @SerialName("email")
    val email: String?
    @SerialName("phone")
    val phone: String?
    @SerialName("career")
    val career: String?
    @SerialName("address")
    val address: String?
    @SerialName("birthday")
    @Serializable(InstantSerializer::class)
    val birthday: Instant?
    @SerialName("facebook")
    val facebook: String?
    @SerialName("instagram")
    val instagram: String?
    @SerialName("twitter")
    val twitter: String?
    @SerialName("linkedin")
    val linkedin: String?
    @SerialName("image")
    val image: String?
    @SerialName("created_at")
    @Serializable(InstantSerializer::class)
    val created_at: Instant
    @SerialName("updated_at")
    @Serializable(InstantSerializer::class)
    val updated_at: Instant
}