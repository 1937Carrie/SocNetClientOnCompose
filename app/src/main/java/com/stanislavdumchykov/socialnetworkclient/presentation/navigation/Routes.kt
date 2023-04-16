package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

sealed class Routes(val route: String) {
    object SignUp : Routes(route = "sign_up")
    object MyProfile : Routes(route = "my_profile")
}