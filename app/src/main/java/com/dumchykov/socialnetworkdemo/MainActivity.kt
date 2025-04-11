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
import androidx.navigation.compose.rememberNavController
import com.dumchykov.socialnetworkdemo.navigation.SNCNavHost
import com.dumchykov.socialnetworkdemo.ui.theme.SocialNetworkClientTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    SNCNavHost(navController, innerPadding)
                }
            }
        }
    }
}