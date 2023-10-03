package com.edu.uniandes.fud.repository

import android.util.Log
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
import kotlinx.coroutines.delay

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

    suspend fun refreshData() {
        database.withTransaction {

            val restaurantList = FudNetService.getRestaurantList()

            Log.d("XD4",database.databaseDao.insertAllRestaurants(restaurantList.asDatabaseModel()).toString())
            val dishesList = FudNetService.getDishList()
            Log.d("XD5",dishesList.asDatabaseModel().listIterator().toString())
            database.databaseDao.insertAllDishes(dishesList.asDatabaseModel())

                delay(4000)
                Log.d("XD5",database.databaseDao.insertAllDishes(dishesList.asDatabaseModel()).size.toString())
                Log.v("XD4",database.databaseDao.getRestaurants().value.toString())
                Log.d("XD4",database.databaseDao.getDishesRestaurant().toString())
                Log.d("XD4",database.databaseDao.getRestaurantsDishes().toString())
                Log.d("XD3_DR",dishesRestaurant.toString())
                Log.d("XD3_DR",dishesRestaurant.toString())
                Log.d("XD3_R",restaurants.toString())
                Log.d("XD3_D",dishes.toString())
                Log.d("XD3_RD",restaurantsDishes.toString())




        }
    }


}