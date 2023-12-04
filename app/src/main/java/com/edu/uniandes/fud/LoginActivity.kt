package com.edu.uniandes.fud

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.login.LoginScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.login.LoginViewModel
import com.edu.uniandes.fud.viewModel.login.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    
    private var startTime: Long = 0

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(this, (application as FuDApplication).repository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MobileAppTheme {
                LoginScreen(loginViewModel)
            }
        }

    }
    
    override fun onStart() {
        super.onStart()
        startTime = System.currentTimeMillis()
        val now : Long = System.currentTimeMillis()
        val elapsedTime : Long = now - FuDApplication.getTimeStart()
        var context : Context = this.applicationContext
        reportFullyDrawn()
        
        lifecycleScope.launch {
            if(!FuDApplication.getReportStatus()) {
                com.edu.uniandes.fud.network.FudNetService.sendStartingApplicationTime(
                    elapsedTime,
                    now,
                    context
                )
                FuDApplication.reportSent()
            }
        }
        
        // Check for network connectivity
        if (!isNetworkConnected(this)) {
            showToast("You are offline. Please check your internet connection.")
            showToast("If you were logged in before, feel free to use fUd.")
        }
        
    }
    
    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    
    // Check for network connectivity
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
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
                "Login Screen",
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
                loginViewModel.onLoginSelected()
            }
            else {
            
            }
        }
    }
    
}