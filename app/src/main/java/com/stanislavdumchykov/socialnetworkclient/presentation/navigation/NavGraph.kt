package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.ContactProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup.SignUpScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.Pages

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SignUp.route
    ) {
        composable(route = Routes.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = "${Routes.ContactProfile.route}/{name}/{career}/{address}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("career") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType },
            ),
        ) {
            ContactProfile(
                navController = navController,
                name = it.arguments?.getString("name") ?: "",
                career = it.arguments?.getString("career") ?: "",
                address = it.arguments?.getString("address") ?: "",
            )
        }
        composable(
            route = "${Routes.Pager.route}/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
            )
        ) {
            Pages(navController = navController, email = it.arguments?.getString("email") ?: "")
        }
    }
}