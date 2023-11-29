package com.edu.uniandes.fud

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.product.ProductScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.product.ProductViewModel
import com.edu.uniandes.fud.viewModel.product.ProductViewModelFactory
import kotlinx.coroutines.launch

class ProductActivity : ComponentActivity() {

    private val newWordActivityRequestCode = 1
    private var startTime: Long = 0
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as FuDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel.setInitialProduct(this.intent.getStringExtra("productId").orEmpty().toInt())
        setContent {
            MobileAppTheme {
                ProductScreen(productViewModel)
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
                "Product Screen",
                context
            )
        }
    }
}