package com.dumchykov.socialnetworkdemo.webapi.data.interceptors

import com.dumchykov.database.models.ContactDBO
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact

fun ContactDBO.toContact(): Contact {
    return Contact(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        career = this.career,
        address = this.address,
        birthday = this.birthday,
        facebook = this.facebook,
        instagram = this.instagram,
        twitter = this.twitter,
        linkedin = this.linkedin,
        image = this.image,
        created_at = this.created_at,
        updated_at = this.updated_at
    )
}