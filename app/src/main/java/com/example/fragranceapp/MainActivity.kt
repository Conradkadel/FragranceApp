package com.example.fragranceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.fragranceapp.navigation.MainNavigation
import com.example.fragranceapp.ui.theme.FragranceAPPTheme
import com.example.fragranceapp.ui.theme.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FragranceAPPTheme {
                Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {

                    MainNavigation()
                }
            }
        }
    }
}

