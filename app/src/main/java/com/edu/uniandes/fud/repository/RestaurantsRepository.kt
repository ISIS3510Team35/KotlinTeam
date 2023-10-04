package com.edu.uniandes.fud.repository

import androidx.room.withTransaction
import com.edu.uniandes.fud.database.DatabaseRestaurant
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.database.asDomainModel
import com.edu.uniandes.fud.domain.Dish
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantDish
import com.edu.uniandes.fud.network.FudNetService
import com.edu.uniandes.fud.network.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBRepository(private val database: DatabaseRoom) {

    val restaurants: Flow<List<Restaurant>> = database.databaseDao().getRestaurants().map{ originalList ->

        val mappedList = originalList.map { restaurant : DatabaseRestaurant ->
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

    val dishes: Flow<List<Dish>> = database.databaseDao().getDishes().map { originalList ->
        originalList.asDomainModel()
    }

    var dishesRestaurant: Flow<List<DishRestaurant>> = database.databaseDao().getDishesRestaurant().map {
            originalList -> originalList.asDomainModel()
    }

    val restaurantsDishes: Flow<List<RestaurantDish>> = database.databaseDao().getRestaurantsDishes().map {
            originalList -> originalList.asDomainModel()
    }
    
    // Refresh data -> Restaurants Repository
    suspend fun refreshData() {
        database.withTransaction {
            val restaurantList = FudNetService.getRestaurantList()
            val dishesList = FudNetService.getDishList()
            // TODO: SOS PONER FUNCION
            //val usersList =  FudNetService.g
            // TODO: SOS LINEA BORRAR
            database.databaseDao().insertRestaurantsAndDishes(dishesList.asDatabaseModel(), restaurantList.asDatabaseModel())
            //database.databaseDao().insertRestaurantsAndDishesAndUser(dishesList.asDatabaseModel(), restaurantList.asDatabaseModel(), )
        }
    }




}