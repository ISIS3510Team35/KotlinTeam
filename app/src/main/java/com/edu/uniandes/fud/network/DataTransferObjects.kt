package com.edu.uniandes.fud.network

import com.edu.uniandes.fud.database.DatabaseProduct
import com.edu.uniandes.fud.database.DatabaseRestaurant
import com.edu.uniandes.fud.database.DatabaseUser
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.User
import com.google.firebase.firestore.GeoPoint
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRestaurantContainer(val restaurants: List<NetworkRestaurant>)

@JsonClass(generateAdapter = true)
data class NetworkRestaurant(
    val id: Int,
    val name: String,
    val rating: Double,
    val location: GeoPoint,
    val image: String)

@JsonClass(generateAdapter = true)
data class NetworkProductContainer(val products: List<NetworkProduct>)
@JsonClass(generateAdapter = true)
data class NetworkProduct(
    val id:Int,
    val name:String,
    val description:String,
    val price: Int,
    val offerPrice: Int,
    val inOffer: Boolean,
    val rating: Double,
    val type: String,
    val category: String,
    val image: String,
    val restaurantId: Int)


@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val users: List<NetworkUser>)

@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id:Int,
    val username:String,
    val password:String
)

fun NetworkRestaurantContainer.asDomainModel(): List<Restaurant>{
    return restaurants.map {
        Restaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            location = GeoPoint(it.location.latitude, it.location.longitude),
            image = it.image)
    }
}

fun NetworkRestaurantContainer.asDatabaseModel(): List<DatabaseRestaurant>{
    return restaurants.map {
        DatabaseRestaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            latitude = it.location.latitude,
            longitude = it.location.longitude,
            image = it.image)
    }
}


fun NetworkProductContainer.asDatabaseModel(): List<DatabaseProduct>{
    return products.map {
        DatabaseProduct(
            id = it.id,
            name = it.name,
            description = it.description,
            price = it.price,
            offerPrice = it.offerPrice,
            inOffer = it.inOffer,
            rating = it.rating,
            type = it.type,
            category = it.category,
            image = it.image,
            restaurantId = it.restaurantId
        )
    }
}

fun NetworkProductContainer.asDomainModel(): List<Product>{
    return products.map {
        Product(
            id = it.id,
            name = it.name,
            description = it.description,
            price = it.price,
            offerPrice = it.offerPrice,
            inOffer = it.inOffer,
            rating = it.rating,
            type = it.type,
            category = it.category,
            image = it.image,
            restaurantId = it.restaurantId
        )
    }
}

fun NetworkUserContainer.asDatabaseModel(): List<DatabaseUser> {
    return users.map {
        DatabaseUser(
            id = it.id,
            username = it.username,
            password = it.password
        )
    }
}

fun NetworkUserContainer.asDomainModel(): List<User>{
    return users.map {
        User(
            id = it.id,
            username = it.username,
            password = it.password
        )
    }
}

