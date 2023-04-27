package com.stanislavdumchykov.socialnetworkclient

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.ContactProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.login.LogInScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup.SignUpExtendedScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup.SignUpScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.Pages
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.NavigationRoutes

@Composable
fun SocialNetworkApp(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LogIn.name
    ) {
        composable(route = NavigationRoutes.LogIn.name) {
            LogInScreen {
                navController.navigate(route = NavigationRoutes.SignUp.name)
            }
        }
        composable(route = NavigationRoutes.SignUp.name) {
            SignUpScreen(
                onSignInClick = {
                    navController.popBackStack()
                },
                onRegisterClick = { email ->
                    navController.navigate(route = "${NavigationRoutes.SignUpExtended.name}/$email")
                }
            )
        }
        composable(
            route = "${NavigationRoutes.SignUpExtended.name}/{email}"
        ) {
            SignUpExtendedScreen(
                onCancelClick = {
                    navController.popBackStack()
                },
                onForwardClick = {
                    navController.navigate(
                        route = "${NavigationRoutes.Pager.name}/${it.arguments?.getString("email") ?: ""}"
                    )
                }
            )
        }
        composable(
            route = "${NavigationRoutes.ContactProfile.name}/{name}/{career}/{address}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("career") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType },
            ),
        ) {
            ContactProfile(
                onArrowClick = { navController.popBackStack() },
                name = it.arguments?.getString("name") ?: "",
                career = it.arguments?.getString("career") ?: "",
                address = it.arguments?.getString("address") ?: "",
            )
        }
        composable(
            route = "${NavigationRoutes.Pager.name}/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
            )
        ) {
            Pages(
                contactListOnItemClick = { name, career, address ->
                    navController.navigate("${NavigationRoutes.ContactProfile.name}/${name}/${career}/${address}")
                },
                email = it.arguments?.getString("email") ?: ""
            )
        }
    }
}