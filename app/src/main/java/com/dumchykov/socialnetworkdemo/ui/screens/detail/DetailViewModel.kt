package com.dumchykov.socialnetworkdemo.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.navigation.parcelableType
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState get() = _detailState.asStateFlow()

    init {
        val typeMap = mapOf(typeOf<Contact>() to parcelableType<Contact>())
        updateState { copy(contact = savedStateHandle.toRoute<Detail>(typeMap).contact) }
    }

    fun updateState(reducer: DetailState.() -> DetailState) {
        _detailState.update(reducer)
    }
}
