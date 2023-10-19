package com.edu.uniandes.fud.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(repository: DBRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _top3Products = MutableLiveData<List<ProductRestaurant>>()
    val top3Products: LiveData<List<ProductRestaurant>> = _top3Products

    private val _offerProducts = MutableLiveData<List<ProductRestaurant>>()
    val offerProducts: LiveData<List<ProductRestaurant>> = _offerProducts

    val dbRepository = repository

    fun onSearchChange(query: String) {
        _query.value = query
    }



    init {
        viewModelScope.launch {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                _top3Products.value = productsRestaurant.sortedBy { it.rating }.subList(0,2)
                _offerProducts.value = productsRestaurant.sortedBy { it.price-it.offerPrice }.subList(0,2)
            }
        }
    }
}

class HomeViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}