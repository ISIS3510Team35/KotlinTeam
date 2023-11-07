package com.edu.uniandes.fud.repository

import android.util.Log
import androidx.room.withTransaction
import com.edu.uniandes.fud.database.DatabaseRestaurant
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.database.asDomainModel
import com.edu.uniandes.fud.domain.Favorite
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.network.FudNetService
import com.edu.uniandes.fud.network.asDatabaseModel
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBRepository(private val database: DatabaseRoom) {

    val restaurants: Flow<List<Restaurant>> = database.databaseDao().getRestaurants().map{ originalList ->

        val mappedList = originalList.map { restaurant : DatabaseRestaurant ->
            val modifiedRestaurant = Restaurant(
                id = restaurant.id,
                name = restaurant.name,
                location = GeoPoint(restaurant.latitude, restaurant.longitude),
                image = restaurant.image)
            modifiedRestaurant
        }

        // Return the mapped list
        mappedList
    }

    val products: Flow<List<Product>> = database.databaseDao().getProducts().map { originalList ->
        originalList.asDomainModel()
    }

    var productsRestaurant: Flow<List<ProductRestaurant>> = database.databaseDao().getProductsRestaurant().map {
            originalList -> originalList.asDomainModel()
    }

    val restaurantsProducts: Flow<List<RestaurantProduct>> = database.databaseDao().getRestaurantsProducts().map {
            originalList -> originalList.asDomainModel()
    }
    
    val users: Flow<List<User>> = database.databaseDao().getUsers().map {
            originalList -> originalList.asDomainModel()
    }

    val favorites: Flow<List<Favorite>> = database.databaseDao().getFavorites().map {
            originalList -> originalList.asDomainModel()
    }
    
    // Insert user
    suspend fun insertUser(id: Int, username: String, password: String) {
        FudNetService.setUser(id, username, password)
    }
    
    // Insert favorite
    /*suspend fun insertFavorite(userId: Int, productId: Int) {
        FudNetService.sendFavorite(userId, productId)
    }*/
    
    // Refresh data -> Restaurants-Products
    suspend fun refreshData() {
        Log.d("XD1","called1")
        database.withTransaction {
            val restaurantList = FudNetService.getRestaurantList()
            val productsList = FudNetService.getProductList()
            val userList =  FudNetService.getUserList()
            val favoritesList = FudNetService.getFavoritesList()
            database.databaseDao().insertRestaurantsAndProductsAndUserAndFavorites(productsList.asDatabaseModel(), restaurantList.asDatabaseModel(), userList.asDatabaseModel(), favoritesList.asDatabaseModel())
        }
    }
    
}