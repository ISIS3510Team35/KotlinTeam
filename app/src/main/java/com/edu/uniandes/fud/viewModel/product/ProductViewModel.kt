package com.edu.uniandes.fud.viewModel.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(repository: DBRepository) : ViewModel() {

    private val _product = MutableLiveData<ProductRestaurant>()
    val product: LiveData<ProductRestaurant> = _product

    private val _top3Products = MutableLiveData<List<ProductRestaurant>>()
    val top3Products: LiveData<List<ProductRestaurant>> = _top3Products

    private val _productId = MutableLiveData<Int>()

    val dbRepository = repository

    fun setInitialProduct(productId: Int) {
        _productId.value = productId
    }

    init {
        viewModelScope.launch {
            repository.productsRestaurant.collect() { productsRestaurant ->
                _product.value = productsRestaurant.find { prod -> _productId.value == prod.id }
                _top3Products.value = productsRestaurant.sortedBy { it.rating } //.subList(0, 3)
            }

        }
    }

}

class ProductViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}