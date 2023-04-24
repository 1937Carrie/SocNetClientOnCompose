package com.stanislavdumchykov.socialnetworkclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.ContactProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.contactlist.ContactList
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.myprofile.MyProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup.SignUpScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SignUp.route
    ) {
        composable(route = Routes.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = "${Routes.MyProfile.route}/{email}") {
            MyProfile(navController, it.arguments?.getString("email") ?: "")
        }
        composable(route = Routes.ContactList.route) {
            ContactList(navController = navController)
        }
        composable(
            route = "${Routes.ContactProfile.route}/{name}/{career}/{address}",
            arguments = listOf(
                navArgument("name") {type = NavType.StringType},
                navArgument("career") {type = NavType.StringType},
                navArgument("address") {type = NavType.StringType},
            ),
        ) {
            ContactProfile(
                navController = navController,
                name = it.arguments?.getString("name") ?: "",
                career = it.arguments?.getString("career") ?: "",
                address = it.arguments?.getString("address") ?: "",
            )
        }
    }
}