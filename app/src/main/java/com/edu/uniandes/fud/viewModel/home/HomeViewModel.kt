package com.edu.uniandes.fud.viewModel.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.domain.Favorite
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.network.FudNetService.Companion.getRecommendedCriteria
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime

@RequiresApi(api = Build.VERSION_CODES.O)
class HomeViewModel(private val context: Context, repository: DBRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _textButtonHour = MutableLiveData<String>()
    val textButtonHour: LiveData<String> = _textButtonHour

    private val _top3Products = MutableLiveData<List<ProductRestaurant>>()
    val top3Products: LiveData<List<ProductRestaurant>> = _top3Products

    private val _top3InteractedRestaurants = MutableLiveData<List<Restaurant>>()
    val top3InteractedRestaurants: LiveData<List<Restaurant>> = _top3InteractedRestaurants

    private val _offerProducts = MutableLiveData<List<ProductRestaurant>>()
    val offerProducts: LiveData<List<ProductRestaurant>> = _offerProducts

    private val _favoriteProducts = MutableLiveData<List<Favorite>>()
    val favoriteProducts: LiveData<List<Favorite>> = _favoriteProducts

    private val _iconRight = MutableLiveData<Int>()
    val iconRight: LiveData<Int> = _iconRight

    private val _isLocationInRange = MutableLiveData<Boolean>()
    val isLocationInRange: LiveData<Boolean> = _isLocationInRange

    private val _favoriteDishes = MutableLiveData<List<ProductRestaurant>>()
    val favoriteDishes: LiveData<List<ProductRestaurant>> = _favoriteDishes

    private val _recommendedDishes = MutableLiveData<List<ProductRestaurant>>()
    val recommendedDishes: LiveData<List<ProductRestaurant>> = _recommendedDishes

    private var previousLocationInRange = false
    private val rangeLatitud = 4.6025
    private val rangeLongitud = -74.0648

    private var favouriteStats = false
    private var promotionStats = false

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private var favList = mutableListOf<ProductRestaurant>()


    fun onSearchChange(query: String) {
        _query.value = query
        if (query.isNotEmpty())
            _iconRight.value = R.drawable.ic_next
        else
            _iconRight.value = R.drawable.ic_sliders
    }

    fun isReadyToChange(): Boolean {
        return _query.value?.isNotEmpty() == true
    }

    fun getQuery(): String {
        return _query.value.orEmpty()
    }

    fun setInitialUserId(userId: Int) {
        _userId.value = userId
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun checkTimeRange(): String {
        val time = LocalTime.now()
        Log.d("TIME",time.toString())
        Log.d("TIME",(time in LocalTime.of(4, 0)..LocalTime.of(10, 59)).toString())
        return when (time) {
            in LocalTime.of(4, 0)..LocalTime.of(10, 59) -> "Hora de Desayuno: Ver todos los platos"
            in LocalTime.of(11, 0)..LocalTime.of(14, 59) -> "Hora de Almuerzo: Ver todos los platos"
            else -> "Hora de Cena: Ver todos los platos"
        }
    }


    init {
        viewModelScope.launch {



            repository.productsRestaurant.collect { productsRestaurant ->
                // Update View with the latest favorite news
                var max = 3

                Log.d("XD_P", productsRestaurant.toString())
                if (productsRestaurant.size < 3) {
                    max = productsRestaurant.size
                }
                val favorites = repository.favorites.first().filter { it.userId == _userId.value }
                for (prod in favorites) {
                    favList.add(productsRestaurant.first { prod.productId == it.id })
                }
                _favoriteDishes.postValue(favList.distinct())

                _top3Products.value =
                    productsRestaurant.sortedBy { it.rating }.asReversed().subList(0, max)

                _offerProducts.value =
                    productsRestaurant.sortedBy { it.price - it.offerPrice }.subList(0, max)
                _textButtonHour.value =
                    checkTimeRange()

                repository.restaurants.collect {restaurants ->
                    Log.d("JAJAJAJA",restaurants.toString())
                    var max = 3
                    if (restaurants.size < 3) {
                        max = restaurants.size
                    }
                    _top3InteractedRestaurants.value =
                        restaurants.asReversed().sortedBy{ it.interactions }.asReversed().subList(0, max)

                }

                val favorites = repository.favorites.first()
                val maxFav = favorites.groupingBy { it.productId }.eachCount().maxByOrNull { it.value }?.key
                _favoriteDishes.postValue(productsRestaurant.filter { it.id == maxFav } )

                val recCriteria = _userId.value?.let { getRecommendedCriteria(it) }
                _recommendedDishes.postValue(productsRestaurant.filter { it.type == recCriteria }.sortedBy { it.rating }.asReversed().subList(0, max))

            }



        }

        viewModelScope.launch {
            Log.d("XD1", "called0")
            repository.refreshData()
            // in case there is a user
            _userId.value?.let { repository.refreshRestaurantInteractedData(it) }
        }

        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationChannel = Channel<Location>()

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                viewModelScope.launch {
                    locationChannel.send(location)
                }
            }

            override fun onProviderDisabled(provider: String) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )

        viewModelScope.launch {
            for (location in locationChannel) {
                val latitude = location.latitude
                val longitude = location.longitude
                val currentLocationInRange = checkLocationInRange(latitude, longitude)
                if (currentLocationInRange != previousLocationInRange) {
                    if (_isLocationInRange.value != currentLocationInRange && currentLocationInRange) {
                        Toast.makeText(
                            context,
                            "Entraste a la zona de la Universidad de los Andes",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    _isLocationInRange.value = currentLocationInRange
                    // Loguea el cambio de ubicación
                    Log.d("XD_Location", "Location changed: ${isLocationInRange.value}")
                    previousLocationInRange = currentLocationInRange


                }
            }
        }
    }

    private fun checkLocationInRange(latitude: Double, longitude: Double): Boolean {
        val latDiff = Math.abs(latitude - rangeLatitud)
        val lonDiff = Math.abs(longitude - rangeLongitud)
        val threshold = 0.05 // Cambia el valor según la precisión deseada
        return latDiff < threshold && lonDiff < threshold
    }

    fun sendPromoReport(context: Context) {
        viewModelScope.launch {
            promotionStats = true
            com.edu.uniandes.fud.network.FudNetService.sendFavPromoReport(
                favouriteStats,
                promotionStats,
                context
            )
        }
        favouriteStats = false
        promotionStats = false
    }

    fun sendFavReport(context: Context) {
        viewModelScope.launch {
            favouriteStats = true
            com.edu.uniandes.fud.network.FudNetService.sendFavPromoReport(
                favouriteStats,
                promotionStats,
                context
            )
        }
        favouriteStats = false
        promotionStats = false
    }
}


class HomeViewModelFactory(private val context: Context, private val repository: DBRepository) :
    ViewModelProvider.Factory {
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(context, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}