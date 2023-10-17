package com.edu.uniandes.fud.viewModel.dish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DishViewModel(repository: DBRepository) : ViewModel() {

    private val _dish = MutableLiveData<DishRestaurant>()
    val dish: LiveData<DishRestaurant> = _dish

    private val _top3Dishes = MutableLiveData<List<DishRestaurant>>()
    val top3Dishes: LiveData<List<DishRestaurant>> = _top3Dishes

    val dbRepository = repository


    init {
        viewModelScope.launch {
            repository.dishesRestaurant.collect() { dishesRestaurant ->
                _dish.value = dishesRestaurant[0]
                _top3Dishes.value = dishesRestaurant.sortedBy { it.rating }.subList(0, 3)
            }
        }
    }

}

class DishViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}