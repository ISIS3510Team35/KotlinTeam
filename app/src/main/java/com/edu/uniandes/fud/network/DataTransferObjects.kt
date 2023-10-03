package com.edu.uniandes.fud.network

import com.edu.uniandes.fud.database.DatabaseDish
import com.edu.uniandes.fud.database.DatabaseRestaurant
import com.edu.uniandes.fud.domain.Dish
import com.edu.uniandes.fud.domain.Restaurant

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRestaurantContainer(val restaurants: List<NetworkRestaurant>)

@JsonClass(generateAdapter = true)
data class NetworkRestaurant(
    val id: Int,
    val name: String,
    val rating: Double,
    val lat: Double,
    val long: Double,
    val thumbnail: String)

@JsonClass(generateAdapter = true)
data class NetworkDishContainer(val dishes: List<NetworkDish>)
@JsonClass(generateAdapter = true)
data class NetworkDish(
    val id:Int,
    val name:String,
    val price: Int,
    val rating: Double,
    val isVeggie: Boolean,
    val isVegan: Boolean,
    val waitingTime: Int,
    val thumbnail: String,
    val restaurantId: Int)

fun NetworkRestaurantContainer.asDomainModel(): List<Restaurant>{
    return restaurants.map {
        Restaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            lat = it.lat,
            long = it.long,
            thumbnail = it.thumbnail)
    }
}

fun NetworkRestaurantContainer.asDatabaseModel(): List<DatabaseRestaurant>{
    return restaurants.map {
        DatabaseRestaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            lat = it.lat,
            long = it.long,
            thumbnail = it.thumbnail)
    }
}


fun NetworkDishContainer.asDatabaseModel(): List<DatabaseDish>{
    return dishes.map {
        DatabaseDish(
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

fun NetworkDishContainer.asDomainModel(): List<Dish>{
    return dishes.map {
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

