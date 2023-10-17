package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.dish.DishScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.dish.DishViewModel
import com.edu.uniandes.fud.viewModel.dish.DishViewModelFactory

class DishActivity : ComponentActivity() {

    private val newWordActivityRequestCode = 1
    private val dishViewModel: DishViewModel by viewModels {
        DishViewModelFactory((application as FuDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppTheme {
                DishScreen(dishViewModel)
            }
        }
    }
}