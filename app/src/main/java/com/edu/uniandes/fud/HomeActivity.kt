package com.edu.uniandes.fud

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.home.HomeScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.home.HomeViewModel
import com.edu.uniandes.fud.viewModel.home.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {

    private val newWordActivityRequestCode = 1
    private var startTime: Long = 0
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(this, (application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setInitialUserId(this.intent.getStringExtra("userId").orEmpty().toInt())
        setContent {
            MobileAppTheme {
                HomeScreen(homeViewModel)
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
                "Home Screen",
                context
            )
        }
    }
}