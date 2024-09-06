package com.dumchykov.socialnetworkdemo.webapi.domain

sealed interface ResponseState {
    data object Initial : ResponseState
    data object Loading : ResponseState
    data class Success<T>(val data: T? = null) : ResponseState
    data class HttpCode(val code: Int, val message: String) : ResponseState
    data class Error(val errorMessage: String?) : ResponseState
}