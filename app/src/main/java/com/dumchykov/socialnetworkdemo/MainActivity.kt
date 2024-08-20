package com.dumchykov.socialnetworkdemo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.navigation.parcelableType
import com.dumchykov.socialnetworkdemo.ui.screens.AddContacts
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import com.dumchykov.socialnetworkdemo.ui.screens.LogIn
import com.dumchykov.socialnetworkdemo.ui.screens.Pager
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
import com.dumchykov.socialnetworkdemo.ui.screens.SignUpExtended
import com.dumchykov.socialnetworkdemo.ui.screens.addcontacts.AddContactsScreen
import com.dumchykov.socialnetworkdemo.ui.screens.detail.DetailScreen
import com.dumchykov.socialnetworkdemo.ui.screens.login.LogInScreen
import com.dumchykov.socialnetworkdemo.ui.screens.pager.PagerScreen
import com.dumchykov.socialnetworkdemo.ui.screens.signup.SignUpScreen
import com.dumchykov.socialnetworkdemo.ui.screens.signupextended.SignUpExtendedScreen
import com.dumchykov.socialnetworkdemo.ui.theme.SocialNetworkClientTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            SocialNetworkClientTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.displayCutout.union(ScaffoldDefaults.contentWindowInsets),
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = LogIn) {
                        composable<LogIn> { LogInScreen(innerPadding, navController) }
                        composable<SignUp> { SignUpScreen(innerPadding, navController) }
                        composable<Detail>(
                            typeMap = mapOf(typeOf<Contact>() to parcelableType<Contact>())
                        ) {
                            DetailScreen(innerPadding, navController)
                        }
                        composable<Pager> { PagerScreen(innerPadding, navController) }
                        composable<SignUpExtended> {
                            SignUpExtendedScreen(
                                padding = innerPadding,
                                navController = navController
                            )
                        }
                        composable<AddContacts> {
                            AddContactsScreen(
                                padding = innerPadding,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
