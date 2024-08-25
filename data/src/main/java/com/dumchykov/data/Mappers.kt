package com.dumchykov.data

import com.dumchykov.database.models.ContactDBO
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact as ContactAPI

fun ContactAPI.toContactDBO(): ContactDBO {
    return ContactDBO(
        id = this.id,
        name = this.name.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        career = this.career.orEmpty(),
        address = this.address.orEmpty(),
        birthday = this.birthday,
        facebook = this.facebook.orEmpty(),
        instagram = this.instagram.orEmpty(),
        twitter = this.twitter.orEmpty(),
        linkedin = this.linkedin.orEmpty(),
        image = this.image.orEmpty(),
        created_at = this.created_at,
        updated_at = this.updated_at
    )
}