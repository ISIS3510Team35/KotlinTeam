package com.edu.uniandes.fud.viewmodel.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.database.getDataBase
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(application: Application) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val dbRepository = DBRepository(getDataBase(application))

    private val dishesRestaurant : LiveData<List<DishRestaurant>> = dbRepository.dishesRestaurant

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    private val _top3Dishes = MutableLiveData<List<DishRestaurant>>(emptyList())
    val top3Dishes: LiveData<List<DishRestaurant>> get() = _top3Dishes

    private val _offerDishes = MutableLiveData<List<DishRestaurant>>(emptyList())
    val offerDishes: LiveData<List<DishRestaurant>> get() = _offerDishes

    init {
        refreshDataFromRepository()
        _top3Dishes.value = dishesRestaurant.value?.sortedBy{ it.rating }?.subList(0,3)
        _offerDishes.value = dishesRestaurant.value?.sortedBy{ it.newPrice }?.takeLastWhile { it.inOffer }?.subList(0,3)

    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                dbRepository.refreshData()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false


            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(dishesRestaurant.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }




}