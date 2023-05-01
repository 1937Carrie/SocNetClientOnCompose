package com.stanislavdumchykov.socialnetworkclient

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stanislavdumchykov.socialnetworkclient.presentation.SharedViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.login.LogInScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.signup.SignUpExtendedScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.signup.SignUpScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.ContactProfile
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.EditProfileScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.Pages
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.NavigationRoutes

@Composable
fun SocialNetworkApp(navController: NavHostController = rememberNavController()) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LogIn.name
    ) {
        composable(route = NavigationRoutes.LogIn.name) {
            LogInScreen(
                sharedViewModel,
                onLoginClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.LogIn.name) {
                        navController.navigate(route = NavigationRoutes.Pager.name)
                    }
                },
                onSignUpClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.LogIn.name) {
                        navController.navigate(route = NavigationRoutes.SignUp.name)
                    }
                }
            )
        }
        composable(route = NavigationRoutes.SignUp.name) {
            SignUpScreen(
                sharedViewModel,
                onSignInClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.SignUp.name) {
                        navController.navigate(route = NavigationRoutes.SignUpExtended.name)
                    }
                }
            )
        }
        composable(route = NavigationRoutes.SignUpExtended.name) {
            SignUpExtendedScreen(
                sharedViewModel,
                onCancelClick = {
                    navController.popBackStack()
                },
                onForwardClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.SignUpExtended.name) {
                        navController.navigate(route = NavigationRoutes.Pager.name)
                    }
                }
            )
        }
        composable(route = NavigationRoutes.Pager.name) {
            Pages(
                onEditProfileClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.Pager.name) {
                        navController.navigate(route = NavigationRoutes.EditProfile.name)
                    }
                },
                contactListOnItemClick = { name, career, address ->
                    if (navController.currentDestination?.route == NavigationRoutes.Pager.name) {
                        navController.navigate("${NavigationRoutes.ContactProfile.name}/${name}/${career}/${address}")
                    }
                },
                sharedViewModel,
            )
        }
        composable(route = NavigationRoutes.EditProfile.name) {
            EditProfileScreen(
                onClick = { navController.popBackStack() }
            )
        }
        composable(route = "${NavigationRoutes.ContactProfile.name}/{name}/{career}/{address}") {
            ContactProfile(
                onArrowClick = {
                    if (navController.currentDestination?.route == NavigationRoutes.ContactProfile.name) {
                        navController.popBackStack()
                    }
                },
                name = it.arguments?.getString("name") ?: "",
                career = it.arguments?.getString("career") ?: "",
                address = it.arguments?.getString("address") ?: "",
            )
        }
    }
}