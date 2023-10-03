package com.edu.uniandes.fud.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.database.getDataBase
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.repository.RestaurantsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val restaurantsRepository = RestaurantsRepository(getDataBase(application))

    val restaurantList = restaurantsRepository.restaurants

    private val _restaurantList = MutableLiveData<List<Restaurant>>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                restaurantsRepository.refreshRestaurants()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(restaurantList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }




}