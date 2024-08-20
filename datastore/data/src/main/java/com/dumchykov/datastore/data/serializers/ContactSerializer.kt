package com.dumchykov.datastore.data.serializers

import androidx.datastore.core.Serializer
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Calendar

object ContactSerializer : Serializer<Contact> {
    override val defaultValue: Contact
        get() = Contact(
            id = 0,
            name = null,
            email = null,
            phone = null,
            career = null,
            address = null,
            birthday = null,
            facebook = null,
            instagram = null,
            twitter = null,
            linkedin = null,
            image = null,
            created_at = Calendar.getInstance(),
            updated_at = Calendar.getInstance()
        )

    override suspend fun readFrom(input: InputStream): Contact {
        return try {
            Json.decodeFromString(
                Contact.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: IOException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Contact, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(Contact.serializer(), t).encodeToByteArray()
            )
        }
    }
}