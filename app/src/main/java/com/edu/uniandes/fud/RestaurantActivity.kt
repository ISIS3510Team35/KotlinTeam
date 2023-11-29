package com.edu.uniandes.fud

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.restaurant.RestaurantScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.restaurant.RestaurantViewModel
import com.edu.uniandes.fud.viewModel.restaurant.RestaurantViewModelFactory
import kotlinx.coroutines.launch

class RestaurantActivity : ComponentActivity() {
    
    private var startTime: Long = 0
    
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
    
    override fun onStart() {
        super.onStart()
        startTime = System.currentTimeMillis()
    }
    
    override fun onStop() {
        super.onStop()
        // Guarda la marca de tiempo cuando la pantalla se detiene
        val endTime = System.currentTimeMillis()
        // Calcula la duración de la actividad
        val duration = endTime - startTime
        var context : Context = this.applicationContext
        
        lifecycleScope.launch {
            com.edu.uniandes.fud.network.FudNetService.sendTimeSpent(
                duration,
                "Restaurant Screen",
                context
            )
        }
    }
}