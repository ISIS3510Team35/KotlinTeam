package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.restaurant.RestaurantScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.restaurant.RestaurantViewModel
import com.edu.uniandes.fud.viewModel.restaurant.RestaurantViewModelFactory

class RestaurantActivity : ComponentActivity() {

    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((application as FuDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantViewModel.setInitialRestaurant(this.intent.getStringExtra("restaurantId").orEmpty().toInt())
        setContent {
            MobileAppTheme {
                RestaurantScreen(restaurantViewModel)
            }
        }
    }
}