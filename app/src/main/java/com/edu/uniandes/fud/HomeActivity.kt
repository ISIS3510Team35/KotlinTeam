package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.home.HomeScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.home.HomeViewModel
import com.edu.uniandes.fud.viewModel.home.HomeViewModelFactory

class HomeActivity : ComponentActivity() {

    private val newWordActivityRequestCode = 1
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}