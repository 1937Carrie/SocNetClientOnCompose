package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stanislavdumchykov.socialnetworkclient.presentation.DrawMyProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.DrawSignUp

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SignUp.route
    ) {
        composable(
            route = Routes.SignUp.route
        ) {
            DrawSignUp(navController = navController)
        }
        composable(
            route = "${Routes.MyProfile.route}/{email}",
        ) {
            DrawMyProfile(navController, it.arguments?.getString("email") ?: "")
        }
    }
}