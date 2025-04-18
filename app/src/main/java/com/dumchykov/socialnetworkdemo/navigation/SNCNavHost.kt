package com.dumchykov.socialnetworkdemo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.dumchykov.socialnetworkdemo.ui.screens.AddContacts
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import com.dumchykov.socialnetworkdemo.ui.screens.EditProfile
import com.dumchykov.socialnetworkdemo.ui.screens.LogIn
import com.dumchykov.socialnetworkdemo.ui.screens.Pager
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
import com.dumchykov.socialnetworkdemo.ui.screens.SignUpExtended
import com.dumchykov.socialnetworkdemo.ui.screens.addcontacts.AddContactsScreen
import com.dumchykov.socialnetworkdemo.ui.screens.detail.DetailScreen
import com.dumchykov.socialnetworkdemo.ui.screens.editprofile.EditProfileScreen
import com.dumchykov.socialnetworkdemo.ui.screens.login.LogInScreen
import com.dumchykov.socialnetworkdemo.ui.screens.pager.PagerScreen
import com.dumchykov.socialnetworkdemo.ui.screens.signup.SignUpScreen
import com.dumchykov.socialnetworkdemo.ui.screens.signupextended.SignUpExtendedScreen

const val DEEP_LINK_URI = "https://www.example.com"

@Composable
fun SNCNavHost(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = LogIn) {
        composable<LogIn> {
            LogInScreen(
                padding = padding,
                onNavigateToSignUp = {
                    navController.navigate(SignUp)
                },
                onNavigateToPager = {
                    navController.navigate(Pager) {
                        popUpTo(LogIn) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<SignUp> {
            SignUpScreen(
                padding = padding,
                onNavigateToSignUpExtended = { navController.navigate(SignUpExtended) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable<Detail>(
            deepLinks = listOf(
                navDeepLink<Detail>(basePath = "$DEEP_LINK_URI/detail")
            )
        ) {
            DetailScreen(
                padding = padding,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable<Pager> {
            PagerScreen(
                padding = padding,
                onNavigateToEditProfile = { navController.navigate(EditProfile) },
                onNavigateToLogIn = {
                    navController.navigate(LogIn) {
                        popUpTo(Pager) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToAddContacts = { navController.navigate(AddContacts) },
                onNavigateToDetail = { contactId ->
                    navController.navigate(Detail(contactId))
                },
            )
        }
        composable<SignUpExtended> {
            SignUpExtendedScreen(
                padding = padding,
                onNavigateToPager = {
                    navController.navigate(Pager) {
                        popUpTo(Pager) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AddContacts> {
            AddContactsScreen(
                padding = padding,
                onNavigateToDetail = { contactId -> navController.navigate(Detail(contactId)) },
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable<EditProfile> {
            EditProfileScreen(
                padding = padding,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
