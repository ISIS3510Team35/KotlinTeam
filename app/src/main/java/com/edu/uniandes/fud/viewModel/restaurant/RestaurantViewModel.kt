package com.edu.uniandes.fud.viewModel.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RestaurantViewModel(repository: DBRepository) : ViewModel() {

    private val _restaurant = MutableLiveData<RestaurantProduct>()
    val restaurant: LiveData<RestaurantProduct> = _restaurant

    private val _restaurantId = MutableLiveData<Int>()


    fun setInitialRestaurant(restaurantId: Int) {
        _restaurantId.postValue(restaurantId)
    }

    init {
        viewModelScope.launch() {
            repository.restaurantsProducts.collect() { restaurantProducts ->
                _restaurant.value = restaurantProducts.find { rest -> _restaurantId.value == rest.id }
            }
        }
    }

}

class RestaurantViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}