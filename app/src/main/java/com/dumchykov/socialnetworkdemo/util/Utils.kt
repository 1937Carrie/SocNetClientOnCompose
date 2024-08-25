package com.dumchykov.socialnetworkdemo.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar

interface BaseContact : Parcelable {
    val id: Int
    val name: String
    val email: String
    val phone: String
    val career: String
    val address: String
    val birthday: Calendar?
    val facebook: String
    val instagram: String
    val twitter: String
    val linkedin: String
    val image: String
    val created_at: Calendar
    val updated_at: Calendar
}

@Parcelize
data class BaseContactImpl(
    override val id: Int = 0,
    override val name: String = "",
    override val email: String = "",
    override val phone: String = "",
    override val career: String = "",
    override val address: String = "",
    override val birthday: Calendar? = null,
    override val facebook: String = "",
    override val instagram: String = "",
    override val twitter: String = "",
    override val linkedin: String = "",
    override val image: String = "",
    override val created_at: Calendar = Calendar.getInstance(),
    override val updated_at: Calendar = Calendar.getInstance(),
) : BaseContact