package com.edu.uniandes.fud.domain

import com.google.firebase.firestore.GeoPoint

data class Restaurant(
    val id:Int,
    val name:String,
    val location: GeoPoint,
    val image: String,
)

data class RestaurantProduct(
    val id:Int,
    val name:String,
    val location: GeoPoint,
    val image: String,
    val products: List<Product>
)

data class Product(
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
    val restaurantId: Int,
)

data class ProductRestaurant(
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
    val restaurantId: Int,
    val restaurant: Restaurant
)
/*
data class ProductUser(
    val id:Int,
    val name:String,
    val price: Int,
    val offerPrice: Int,
    val inOffer: Boolean,
    val rating: Double,
    val type: String,
    val image: String,
    val restaurantId: Int,
    val users: List<User>
)
 */

data class User(
    val id: Int,
    val username: String,
    val password: String
) {

}

/*
data class UserProduct(
    val id: Int,
    val username: String,
    val password: String,
    val products: List<Product>
)
 */