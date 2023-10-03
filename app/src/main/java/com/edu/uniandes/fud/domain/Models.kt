package com.edu.uniandes.fud.domain

data class Restaurant(
    val id:Int,
    val name:String,
    val rating: Double,
    val lat: Double,
    val long: Double,
    val thumbnail:String
)

data class RestaurantDish(
    val id:Int,
    val name:String,
    val rating: Double,
    val lat: Double,
    val long: Double,
    val thumbnail:String,
    val dishes: List<Dish>
)

data class Dish(
    val id:Int,
    val name:String,
    val price: Int,
    val newPrice: Int,
    val inOffer: Boolean,
    val rating: Double,
    val isVeggie: Boolean,
    val isVegan: Boolean,
    val waitingTime: Int,
    val thumbnail: String,
    val restaurantId: Int,
)

data class DishRestaurant(
    val id:Int,
    val name:String,
    val price: Int,
    val newPrice: Int,
    val inOffer: Boolean,
    val rating: Double,
    val isVeggie: Boolean,
    val isVegan: Boolean,
    val waitingTime: Int,
    val thumbnail: String,
    val restaurantId: Int,
    val restaurant: Restaurant
)
