package com.edu.uniandes.fud.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.database.asDomainModel
import com.edu.uniandes.fud.domain.Dish
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantDish
import com.edu.uniandes.fud.network.FudNetService
import com.edu.uniandes.fud.network.asDatabaseModel

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

    val dishes: LiveData<List<Dish>> = database.databaseDao.getDishes().map { originalList ->
        originalList.asDomainModel()
    }

    var dishesRestaurant: LiveData<List<DishRestaurant>> = database.databaseDao.getDishesRestaurant().map {
        originalList ->
        originalList.asDomainModel()
    }

    val restaurantsDishes: LiveData<List<RestaurantDish>> = database.databaseDao.getRestaurantsDishes().map {
        originalList -> originalList.asDomainModel()
    }
    
    // Refresh data -> Restaurants Repository
    suspend fun refreshData() {
        database.withTransaction {
            val restaurantList = FudNetService.getRestaurantList()
            val dishesList = FudNetService.getDishList()
            database.databaseDao.insertRestaurantsAndDishes(dishesList.asDatabaseModel(), restaurantList.asDatabaseModel())
        }
    }


}