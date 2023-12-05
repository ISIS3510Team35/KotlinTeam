package com.edu.uniandes.fud


import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.forgotPW.ForgotPWScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.forgotPW.ForgotPWViewModel
import com.edu.uniandes.fud.viewModel.forgotPW.ForgotPWViewModelFactory
import kotlinx.coroutines.launch


class ForgotPWActivity : ComponentActivity() {
    
    private var startTime: Long = 0
    
    private val forgotPWViewModel: ForgotPWViewModel by viewModels {
        ForgotPWViewModelFactory(this, (application as FuDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MobileAppTheme {
                ForgotPWScreen(forgotPWViewModel)
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
                "ForgotPW Screen",
                context
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                forgotPWViewModel.onForgotPWSelected()
            }
        }
    }
}