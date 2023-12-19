package com.edu.uniandes.fud.viewModel.search

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.repository.DBRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(repository: DBRepository) : ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var context: Context

    lateinit var repository: DBRepository

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _myLocation = MutableLiveData<Location>()
    val myLocation: LiveData<Location> = _myLocation

    private val _results = MutableLiveData<List<RestaurantProduct>>()
    val results: LiveData<List<RestaurantProduct>> = _results

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

    var veggieStats = false
    var veganStats  = false
    var priceStats  = false

    fun onSearchChange(query: String) {
        _query.value = query
        viewModelScope.launch() {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                val productRestaurants: List<ProductRestaurant> = productsRestaurant.filter {
                    _query.value.orEmpty() !="" && it.name.startsWith(_query.value.orEmpty(), true)
                }
                _results.value = getAsRestaurantProduct(productRestaurants)

            }
        }
        getLastKnownLocation()
    }

    fun setActivity(context: Context) {
        this.context = context
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        getLastKnownLocation()
    }

    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    _myLocation.value = location
                    Log.d("XD_UBIC","Ubicación encontrada ${location.longitude} ${location.latitude}")
                }
            }
    }


    fun getAsRestaurantProduct(productRestaurants: List<ProductRestaurant>) : List<RestaurantProduct>{
        return productRestaurants.groupBy { it.restaurant }.map { (restaurant, products) ->
            RestaurantProduct(
                id = restaurant.id,
                name = restaurant.name,
                location = restaurant.location,
                image = restaurant.image,
                products = products.map { product ->
                    Product(
                        id = product.id,
                        name = product.name,
                        description = product.description,
                        price = product.price,
                        offerPrice = product.offerPrice,
                        inOffer = product.inOffer,
                        rating = product.rating,
                        type = product.type,
                        category = product.category,
                        image = product.image,
                        restaurantId = product.restaurantId
                    )
                }
            )
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
        viewModelScope.launch() {
            com.edu.uniandes.fud.network.FudNetService.sendFilterReport(
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
        viewModelScope.launch() {
            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                _results.value = getAsRestaurantProduct(productsRestaurant.filter { _query.value.orEmpty() !="" && it.name.startsWith(_query.value.orEmpty(), true)})
            }
            getLastKnownLocation()
        }
        viewModelScope.launch() {
            Log.d("XD2","called0")
            repository.refreshData()
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