package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

sealed class Routes(val route: String) {
    object SignUp : Routes(route = "sign_up")
    object ContactProfile : Routes(route = "contact_profile")
    object Pager : Routes(route = "pager")
}