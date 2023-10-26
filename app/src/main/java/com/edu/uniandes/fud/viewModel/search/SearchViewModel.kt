package com.edu.uniandes.fud.viewModel.search

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(repository: DBRepository) : ViewModel() {
    lateinit var repository: DBRepository

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _results = MutableLiveData<List<ProductRestaurant>>()
    val results: LiveData<List<ProductRestaurant>> = _results

    private val _visibleFilters = MutableLiveData<Boolean>()
    val visibleFilters: LiveData<Boolean> = _visibleFilters

    private val _veggieBoolean = MutableLiveData<Boolean>()
    val veggieBoolean: LiveData<Boolean> = _veggieBoolean

    private val _veganBoolean = MutableLiveData<Boolean>()
    val veganBoolean: LiveData<Boolean> = _veganBoolean

    private val _pricePosition = MutableLiveData<Float>()
    val pricePosition: LiveData<Float> = _pricePosition

    private val _colorTintIcon = MutableLiveData<Color>()
    val colorTintIcon: LiveData<Color> = _colorTintIcon

    private val _colorBackIcon = MutableLiveData<Color>()
    val colorBackIcon: LiveData<Color> = _colorBackIcon

    val dbRepository = repository

    var veggieStats = false
    var veganStats  = false
    var priceStats  = false

    fun onSearchChange(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                _results.value = productsRestaurant.filter {
                    _query.value.orEmpty() !="" && it.name.startsWith(_query.value.orEmpty(), true)
                }

            }
        }
    }

    fun onChangePriceFilter(sliderPosition: Float) {
        _pricePosition.value = sliderPosition
        veggieStats = true
    }

    fun onChangeVeganFilter(selected: Boolean) {
        _veganBoolean.value = selected
        veganStats = true
    }

    fun onChangeVeggieFilter(selected: Boolean) {
        _veggieBoolean.value = selected
        priceStats = true
    }

    fun toggleFilter(){
        if(_visibleFilters.value == true){
            _visibleFilters.value = false
            _colorTintIcon.value = Color.Black
            _colorBackIcon.value = Color.White
        }
        else{
            _visibleFilters.value = true
            _colorTintIcon.value = Color.White
            _colorBackIcon.value = Color.Black
        }
    }




    fun applyFilters(context: Context) {
        viewModelScope.launch {
            com.edu.uniandes.fud.network.FudNetService.sendReport(
                veggieStats,
                veganStats,
                priceStats,
                context
            )
        }
        veganStats = false
        veggieStats = false
        priceStats = false
    }

    fun setInitialQuery(query: String) {
        _query.value = query
    }

    init {
        this.repository = repository
        viewModelScope.launch {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                _results.value = productsRestaurant.filter { _query.value.orEmpty() !="" && it.name.startsWith(_query.value.orEmpty(), true)}
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