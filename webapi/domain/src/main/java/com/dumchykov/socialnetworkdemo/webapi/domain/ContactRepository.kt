package com.dumchykov.socialnetworkdemo.webapi.domain

import com.dumchykov.data.Contact

interface ContactRepository {
    /**
     * Registers a new user account by sending the provided email and password to a REST API.
     *
     * This function makes a request to the REST API with the given credentials. If the HTTP response code
     * is 200 (indicating success), the account information is saved to the local Room database. The function
     * then returns [ResponseState.Success].
     *
     * If the registration request fails or the HTTP response code is not 200, the function returns
     * a different [ResponseState] (e.g., [ResponseState.Error] or [ResponseState.HttpCode]).
     *
     * @param email The email address to register the new account.
     * @param password The password for the new account.
     * @return [ResponseState] representing the result of the registration, either success or an error state.
     *
     * @see ResponseState
     */
    suspend fun register(email: String, password: String): ResponseState

    /**
     * Authorizes the user account by sending the provided email and password to a REST API.
     *
     * This function makes a request to the REST API with the given credentials. If the HTTP response code
     * is 200 (indicating success), the account information is saved to the local Room database. The function
     * then returns [ResponseState.Success].
     *
     * If the authorization request fails or the HTTP response code is not 200, the function returns
     * a different [ResponseState] (e.g., [ResponseState.Error] or [ResponseState.HttpCode]).
     *
     * @param email The email address to register the new account.
     * @param password The password for the new account.
     * @return [ResponseState] representing the result of the registration, either success or an error state.
     *
     * @see ResponseState
     */
    suspend fun authorize(email: String, password: String): ResponseState

    /**
     * Refreshes the access token using the provided refresh token by making a request to the REST API.
     *
     * This function sends the given refresh token to the `contactApiService` to request a new access token.
     * If the HTTP response code is 200, it extracts the new access token and refresh token from the
     * response data, and returns [ResponseState.Success].
     *
     * If the response code is not 200, the function returns [ResponseState.HttpCode] with the specific
     * HTTP status code and message.
     *
     * If an exception occurs during the request, it returns [ResponseState.Error] with the exception message.
     *
     * @param refreshToken The refresh token used to request a new access token.
     * @return [ResponseState] representing the result of the token refresh operation, either success
     * or an error state.
     *
     * @see ResponseState
     */
    suspend fun refreshToken(): ResponseState

    /**
     * Updates the user information by sending the provided user details to the REST API.
     *
     * This function takes a bearer token for authentication and the updated user details encapsulated
     * in an [Contact] object. It makes a request to the REST API with the updated user details. If the API
     * responds with an HTTP code 200 (success), the response data is converted into a `ContactDBO` object, and
     * the updated user information is stored in the local Room database.
     *
     * If the response code is not 200, the function returns [ResponseState.HttpCode] with the corresponding
     * HTTP status code and message.
     *
     * If an exception occurs during the operation, it catches the exception and returns [ResponseState.Error]
     * containing the error message.
     *
     * @param bearerToken The authentication token for authorizing the API request.
     * @param user The new user details to update, encapsulated in an [Contact] object.
     * @return [ResponseState] representing the result of the update operation, which could be a success,
     * an HTTP status code, or an error.
     *
     * @see ResponseState
     */
    suspend fun editUser(user: Contact): ResponseState

    /**
     * Retrieves a list of all users from the REST API and stores them in the local Room database.
     *
     * This function makes a request to the REST API to fetch the list of users using the provided
     * bearer token for authentication. If the API responds with an HTTP code 200 (success), the user data
     * is mapped to `ContactDBO` objects, which are then inserted into the local Room database.
     *
     * If the response code is not 200, the function returns [ResponseState.HttpCode] containing the HTTP
     * status code and message.
     *
     * If an exception occurs during the operation, it catches the exception and returns [ResponseState.Error]
     * with the error message.
     *
     * @param bearerToken The authentication token used for making the API request.
     * @return [ResponseState] representing the result of the get users operation, which could be a success,
     * an HTTP status code, or an error.
     *
     * @see ResponseState
     */
    suspend fun getUsers(): ResponseState

    suspend fun getUserById(id: Int): Contact

    /**
     * Adds a contact for the current user by sending a request to the REST API.
     *
     * This function makes a request to the REST API to add a contact with the given `contactId`
     * to the user's contacts list, using the provided bearer token for authentication. If the API
     * responds with an HTTP code 200 (success), it returns [ResponseState.Success] containing the updated
     * list of [Contact].
     *
     * If the response code is not 200, the function returns [ResponseState.HttpCode] with the corresponding
     * HTTP status code and message.
     *
     * If an exception occurs during the operation, it catches the exception and returns [ResponseState.Error]
     * with the error message.
     *
     * @param bearerToken The authentication token used for making the API request.
     * @param contactId The ID of the contact to be added.
     * @return [ResponseState] representing the result of the add contact operation, which could be a success
     * with the updated list of [Contact], an HTTP status code, or an error.
     *
     * @see ResponseState
     */
    suspend fun addContact(contactId: Int): ResponseState

    /**
     * Deletes a contact for the current user by sending a request to the REST API.
     *
     * This function makes a request to the REST API to delete a contact with the specified `contactId`
     * from the user's contacts list, using the provided bearer token for authentication. If the API responds
     * with an HTTP code 200 (success), it returns [ResponseState.Success] containing the updated list of contacts.
     *
     * If the response code is not 200, the function returns [ResponseState.HttpCode] with the corresponding
     * HTTP status code and message.
     *
     * If an exception occurs during the operation, it catches the exception and returns [ResponseState.Error]
     * with the error message.
     *
     * @param contactId The ID of the contact to be deleted.
     * @param bearerToken The authentication token used for making the API request.
     * @return [ResponseState] representing the result of the delete contact operation, which could be a success
     * with the updated contact list, an HTTP status code, or an error.
     *
     * @see ResponseState
     */
    suspend fun deleteContact(contactId: Int): ResponseState

    /**
     * This function retrieves a list of contacts from the database.
     *
     * @return [ResponseState.Success] containing a list of [Contact] on success,
     * or other possible [ResponseState] types like [ResponseState.Error] in case of failure.
     *
     * @see ResponseState
     */
    suspend fun getUserContacts(): ResponseState
}