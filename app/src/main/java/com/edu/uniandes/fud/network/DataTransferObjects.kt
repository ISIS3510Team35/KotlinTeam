package com.edu.uniandes.fud.network

import com.edu.uniandes.fud.database.DatabaseRestaurant
import com.edu.uniandes.fud.domain.Restaurant

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRestaurantContainer(val restaurants: List<NetworkRestaurant>)

@JsonClass(generateAdapter = true)
data class NetworkRestaurant(
    val id: Int,
    val name: String,
    val rating: String,
    val lat: Double,
    val long: Double,
    val thumbnail: String)

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