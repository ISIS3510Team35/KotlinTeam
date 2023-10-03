package com.edu.uniandes.fud.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edu.uniandes.fud.domain.Restaurant

@Entity
data class DatabaseRestaurant(
    @PrimaryKey
    val id: Int,
    val name: String,
    val rating: String,
    val lat: Double,
    val long: Double,
    val thumbnail: String)



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