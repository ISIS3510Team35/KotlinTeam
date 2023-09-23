package com.edu.uniandes.gastronomicoffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.edu.uniandes.gastronomicoffer.ui.FuDTheme
import com.edu.uniandes.gastronomicoffer.ui.HomeScreen

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuDTheme {
                HomeScreen()
            }
        }
    }
}