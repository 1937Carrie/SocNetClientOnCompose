package com.stanislavdumchykov.socialnetworkclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stanislavdumchykov.socialnetworkclient.presentation.screens.my_profile.MyProfileScreen
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.SocialNetworkClientTheme
import com.stanislavdumchykov.socialnetworkclient.util.KEY_EMAIL

class MyProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val email = intent.getStringExtra(KEY_EMAIL) ?: ""
        setContent {
            SocialNetworkClientTheme(
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MyProfileScreen(email = email, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}