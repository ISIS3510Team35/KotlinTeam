package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.edu.uniandes.fud.ui.restaurant.RestaurantScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme

class RestaurantActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppTheme {
                RestaurantScreen()
            }
        }
    }
}