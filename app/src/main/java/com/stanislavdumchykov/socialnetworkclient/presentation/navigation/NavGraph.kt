package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.contactlist.ContactList
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.myprofile.MyProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup.SignUpScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SignUp.route
    ) {
        composable(
            route = Routes.SignUp.route
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = "${Routes.MyProfile.route}/{email}",
        ) {
            MyProfile(navController, it.arguments?.getString("email") ?: "")
        }
        composable(
            route = Routes.ContactList.route
        ) {
            ContactList(navController = navController)
        }
    }
}