package com.dumchykov.socialnetworkdemo.webapi.domain

/**
 * Sealed interface representing the different possible states of a network response.
 * This can be used to model various stages in the lifecycle of a request, such as loading,
 * success, error, etc.
 *
 * States:
 * - [Initial]: Represents the initial state, typically before a request has been started.
 * - [Loading]: Represents a loading state, usually indicating that a request is in progress.
 * - [Success]: Represents a successful response. It can hold optional data of type [T].
 * - [HttpCode]: Represents a response with a specific HTTP status code and message, for cases where
 *   the response contains meaningful HTTP status information.
 * - [Error]: Represents an error state, holding an optional error message describing the issue.
 */
sealed interface ResponseState {
    data object Initial : ResponseState
    data object Loading : ResponseState
    data class Success<T>(val data: T? = null) : ResponseState
    data class HttpCode(val code: Int, val message: String) : ResponseState
    data class Error(val errorMessage: String?) : ResponseState
}