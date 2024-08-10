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
import androidx.navigation.toRoute
import com.dumchykov.socialnetworkdemo.ui.screens.MyContacts
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.MyContactsScreen
import com.dumchykov.socialnetworkdemo.ui.screens.MyProfile
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
import com.dumchykov.socialnetworkdemo.ui.screens.myprofile.MyProfileScreen
import com.dumchykov.socialnetworkdemo.ui.screens.signup.SignUpScreen
import com.dumchykov.socialnetworkdemo.ui.theme.SocialNetworkClientTheme

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
                    NavHost(navController = navController, startDestination = SignUp) {
                        composable<MyProfile> { backStackEntry ->
                            val myProfile: MyProfile = backStackEntry.toRoute()
                            MyProfileScreen(innerPadding, navController, myProfile)
                        }
                        composable<SignUp> { SignUpScreen(innerPadding, navController) }
                        composable<MyContacts> { MyContactsScreen(innerPadding, navController) }
                    }
                }
            }
        }
    }
}
