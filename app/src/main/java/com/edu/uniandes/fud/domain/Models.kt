package com.edu.uniandes.fud.domain

import com.google.firebase.firestore.GeoPoint

data class Restaurant(
    val id:Int,
    val name:String,
    val location: GeoPoint,
    val image: String,
	val interactions: Int = 0
)

data class RestaurantProduct(
    val id:Int,
    val name:String,
    val location: GeoPoint,
    val image: String,
	val interactions: Int = 0,
    val products: List<Product>
)

data class Product(
	val id:Int,
	val name:String,
	val description:String,
	val price: Double,
	val offerPrice: Double,
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
	val price: Double,
	val offerPrice: Double,
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
	val name: String,
	val number: String,
	val password: String,
	val documentId: String
) {

}

data class Favorite(
	val userId: Int,
	val productId: Int
)

/*
data class UserProduct(
    val id: Int,
    val username: String,
    val password: String,
    val products: List<Product>
)
 */