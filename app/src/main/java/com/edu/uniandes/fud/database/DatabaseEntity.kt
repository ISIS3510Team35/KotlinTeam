package com.edu.uniandes.fud.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edu.uniandes.fud.domain.Dish
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantDish

@Entity
data class DatabaseRestaurant(
    @PrimaryKey
    val id: Int,
    val name: String,
    val rating: Double,
    val lat: Double,
    val long: Double,
    val thumbnail: String
)

@Entity
data class DatabaseDish(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Int,
    val rating: Double,
    val isVeggie: Boolean,
    val isVegan: Boolean,
    val waitingTime: Int,
    val thumbnail: String,
    val restaurantId: Int
)

@JvmName("restaurantAsDomainModel")
fun List<DatabaseRestaurant>.asDomainModel(): List<Restaurant> {
    return map {
        Restaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            lat = it.lat,
            long = it.long,
            thumbnail = it.thumbnail)
    }
}

@JvmName("dishAsDomainModel")
fun List<DatabaseDish>.asDomainModel(): List<Dish> {
    return map {
        Dish(
            id = it.id,
            name = it.name,
            price = it.price,
            rating = it.rating,
            isVeggie = it.isVeggie,
            isVegan = it.isVegan,
            waitingTime = it.waitingTime,
            thumbnail = it.thumbnail,
            restaurantId = it.restaurantId
           )
    }
}

fun Map<DatabaseRestaurant,List<DatabaseDish>>.asDomainModel(): List<RestaurantDish> {
    return map {
        val result = mutableListOf<RestaurantDish>()
        forEach { (restaurant, dishDatabases) ->
            val dishes = dishDatabases.map { dishDb ->
                Dish(
                    id = dishDb.id,
                    name = dishDb.name,
                    price = dishDb.price,
                    rating = dishDb.rating,
                    isVeggie = dishDb.isVeggie,
                    isVegan = dishDb.isVegan,
                    waitingTime = dishDb.waitingTime,
                    thumbnail = dishDb.thumbnail,
                    restaurantId = dishDb.restaurantId
                )
            }

            result.add(
                RestaurantDish(
                    id = restaurant.id,
                    name = restaurant.name,
                    rating = restaurant.rating,
                    lat = restaurant.lat,
                    long = restaurant.long,
                    thumbnail = restaurant.thumbnail,
                    dishes = dishes  // Include the associated list of dishes
                )
            )
        }
        return result
    }
}