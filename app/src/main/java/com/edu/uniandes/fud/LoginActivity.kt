package com.edu.uniandes.fud

import android.content.pm.PackageManager
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
        LoginViewModelFactory(this, (application as FuDApplication).repository)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loginViewModel.onLoginSelected()
            }
            else {
            
            }
        }
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