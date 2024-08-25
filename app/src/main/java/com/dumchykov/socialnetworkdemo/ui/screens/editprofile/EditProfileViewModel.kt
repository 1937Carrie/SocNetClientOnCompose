package com.dumchykov.socialnetworkdemo.ui.screens.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
) : ViewModel() {
    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState get() = _editProfileState.asStateFlow()

    init {
        updateContactAppearance()
    }

    private fun updateContactAppearance() {
        viewModelScope.launch {
            val contact = contactRepository.getUser()
            updateState {
                copy(
                    name = contact.name.orEmpty(),
                    career = contact.career.orEmpty(),
                    email = contact.email.orEmpty(),
                    phone = contact.phone.orEmpty(),
                    address = contact.address.orEmpty(),
                    dateOfBirth = contact.birthday,
                    dataHasActualState = true
                )
            }
        }
    }

    /**
     * From "dd.MM.yyyy" to Calendar instance
     */
    fun convertToCalendar(dateString: String): Calendar {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date: Date =
            formatter.parse(dateString) ?: throw IllegalArgumentException("Invalid date format")

        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    /**
     * From Calendar type to "dd.MM.yyyy" to string format
     */
    fun convertCalendarToString(calendar: Calendar?): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return calendar?.time?.let { dateFormat.format(it) } ?: ""
    }

    fun updateState(reducer: EditProfileState.() -> EditProfileState) {
        _editProfileState.update(reducer)
    }

    fun editContact() {
        viewModelScope.launch {
            val contact = contactRepository.getUser()
            val updatedContact = editProfileState.value
            val editedUser = contact.copy(
                name = updatedContact.name,
                career = updatedContact.career,
                email = updatedContact.email,
                phone = updatedContact.phone,
                address = updatedContact.address,
                birthday = updatedContact.dateOfBirth
            )
            contactRepository.editUser(editedUser)
            updateContactAppearance()
        }
    }
}