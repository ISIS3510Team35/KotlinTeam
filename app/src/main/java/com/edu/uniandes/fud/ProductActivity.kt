package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.product.ProductScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.product.ProductViewModel
import com.edu.uniandes.fud.viewModel.product.ProductViewModelFactory

class ProductActivity : ComponentActivity() {

    private val newWordActivityRequestCode = 1
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
}