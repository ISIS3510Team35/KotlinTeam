package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.login.LoginScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.login.LoginViewModel
import com.edu.uniandes.fud.viewModel.login.LoginViewModelFactory

class LoginActivity : ComponentActivity() {
    
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MobileAppTheme {
                LoginScreen(loginViewModel)
            }
        }
    }
    
}