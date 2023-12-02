package com.edu.uniandes.fud.viewModel.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.launch

class ListViewModel(private val context: Context, repository: DBRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        viewModelScope.launch {
            repository.restaurantsProducts.collect { restaurantsProducts ->
                for (restaurant: RestaurantProduct in restaurantsProducts) {

                }
            }
        }
    }
}

class ListViewModelFactory(private val context: Context, private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(context, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}