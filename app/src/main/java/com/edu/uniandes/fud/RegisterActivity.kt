package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.register.RegisterScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.register.RegisterViewModel
import com.edu.uniandes.fud.viewModel.register.RegisterViewModelFactory

class RegisterActivity : ComponentActivity() {
    
    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory((application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MobileAppTheme {
                RegisterScreen(registerViewModel)
            }
        }
    }
    
}