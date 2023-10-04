package com.edu.uniandes.fud.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(repository: DBRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _top3Dishes = MutableLiveData<List<DishRestaurant>>()
    val top3Dishes: LiveData<List<DishRestaurant>> = _top3Dishes

    private val _offerDishes = MutableLiveData<List<DishRestaurant>>()
    val offerDishes: LiveData<List<DishRestaurant>> = _offerDishes

    val dbRepository = repository

    fun onSearchChange(query: String) {
        _query.value = query
    }



    init {
        viewModelScope.launch {
            repository.dishesRestaurant.collect { dishesRestaurant ->
                // Update View with the latest favorite news
                _top3Dishes.value = dishesRestaurant.sortedBy { it.rating }.subList(0,3)
                _offerDishes.value = dishesRestaurant.sortedBy { it.price-it.newPrice }.subList(0,3)
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