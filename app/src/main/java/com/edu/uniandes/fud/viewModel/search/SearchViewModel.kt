package com.edu.uniandes.fud.viewModel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(repository: DBRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _results = MutableLiveData<List<RestaurantProduct>>()
    val results: LiveData<List<RestaurantProduct>> = _results


    val dbRepository = repository

    fun onSearchChange(query: String) {
        _query.value = query
    }

    fun setInitialQuery(query: String) {
        _query.value = query
    }

    init {
        viewModelScope.launch {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                //_results.value = productsRestaurant.map{it.name}.filter{ it.startsWith(query.value) }
            }
        }
    }
}

class SearchViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}