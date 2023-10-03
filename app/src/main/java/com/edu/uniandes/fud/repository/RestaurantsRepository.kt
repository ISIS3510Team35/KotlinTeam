package com.edu.uniandes.fud.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.network.FudNetService
import com.edu.uniandes.fud.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBRepository(private val database: DatabaseRoom) {

    val restaurants: LiveData<List<Restaurant>> = database.databaseDao.getRestaurants().map { originalList ->

        val mappedList = originalList.map { restaurant ->
            val modifiedRestaurant = Restaurant(
                id = restaurant.id,
                name = restaurant.name,
                rating = restaurant.rating,
                lat = restaurant.lat,
                long = restaurant.long,
                thumbnail = restaurant.thumbnail)
            modifiedRestaurant
        }

        // Return the mapped list
        mappedList
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val restaurantList = FudNetService.getRestaurantList()
            database.databaseDao.insertAllRestaurants(restaurantList.asDatabaseModel())
            val dishesList = FudNetService.getDishList()
            database.databaseDao.insertAllDishes(dishesList.asDatabaseModel())
        }
    }


}