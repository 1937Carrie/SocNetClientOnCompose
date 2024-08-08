package com.dumchykov.socialnetworkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.ui.Modifier
import com.dumchykov.socialnetworkdemo.ui.screens.MyProfileScreen
import com.dumchykov.socialnetworkdemo.ui.theme.SocialNetworkClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialNetworkClientTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.displayCutout.union(ScaffoldDefaults.contentWindowInsets),
                ) { innerPadding ->
                    MyProfileScreen(innerPadding)
                }
            }
        }
    }
}
