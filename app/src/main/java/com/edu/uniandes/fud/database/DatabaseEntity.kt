package com.edu.uniandes.fud.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edu.uniandes.fud.domain.Dish
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantDish
import com.edu.uniandes.fud.domain.User

@Entity
data class DatabaseRestaurant(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var rating: Double = 0.0,
    var lat: Double = 0.0,
    var long: Double = 0.0,
    var thumbnail: String = ""
)

@Entity
data class DatabaseDish(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var price: Int = 0,
    var newPrice: Int = 0,
    var inOffer: Boolean = false,
    var rating: Double = 0.0,
    var isVeggie: Boolean = false,
    var isVegan: Boolean = false,
    var waitingTime: Int = 0,
    var thumbnail: String = "",
    var restaurantId: Int = 0
)

@Entity
data class DatabaseUser(
    @PrimaryKey
    var id: Int = 0,
    var username: String = "",
    var password: String = ""
)
// TODO: In the future add new dataClass for offers and prices independent to dishes

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
            newPrice = it.price,
            inOffer = it.inOffer,
            rating = it.rating,
            isVeggie = it.isVeggie,
            isVegan = it.isVegan,
            waitingTime = it.waitingTime,
            thumbnail = it.thumbnail,
            restaurantId = it.restaurantId
           )
    }
}

@JvmName("userAsDomainModel")
fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.id,
            username = it.username,
            password = it.password
        )
    }
}

@JvmName("restaurantDishAsDomainModel")
fun Map<DatabaseRestaurant,List<DatabaseDish>>.asDomainModel(): List<RestaurantDish> {
    return map {
        val result = mutableListOf<RestaurantDish>()
        forEach { (restaurant, dishDatabases) ->
            val dishes = dishDatabases.map { dishDb ->
                Dish(
                    id = dishDb.id,
                    name = dishDb.name,
                    price = dishDb.price,
                    newPrice = dishDb.newPrice, // TODO: Should be OldPrice but needs to be consulted with FlutterTeam
                    inOffer = dishDb.inOffer,
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

@JvmName("dishRestaurantAsDomainModel")
fun Map<DatabaseDish, DatabaseRestaurant>.asDomainModel(): List<DishRestaurant> {
    return map {
        val result = mutableListOf<DishRestaurant>()
        forEach { (dish, restaurant) ->
            val restaurantObj = Restaurant(
                id = restaurant.id,
                name = restaurant.name,
                rating = restaurant.rating,
                lat = restaurant.lat,
                long = restaurant.long,
                thumbnail = restaurant.thumbnail
            )

            result.add(
                DishRestaurant(
                    id = dish.id,
                    name = dish.name,
                    price = dish.price,
                    newPrice = dish.newPrice,
                    inOffer = dish.inOffer,
                    rating = dish.rating,
                    isVeggie = dish.isVeggie,
                    isVegan = dish.isVegan,
                    waitingTime = dish.waitingTime,
                    thumbnail = dish.thumbnail,
                    restaurantId = dish.restaurantId,
                    restaurant = restaurantObj
                )
            )
        }
        return result
    }
}

/*
@JvmName("dishUserAsDomainModel")
fun Map<DatabaseDish, DatabaseUser>.asDomainModel(): List<DishUser> {
    return map {
        val result = mutableListOf<DishUser>()
        forEach { (dish, user) ->
            val userObj = User(
                id = user.id,
                username = user.username,
                password = user.password
            )
            
            result.add(
                DishUser(
                    id = dish.id,
                    name = dish.name,
                    price = dish.price,
                    newPrice = dish.newPrice,
                    inOffer = dish.inOffer,
                    rating = dish.rating,
                    isVeggie = dish.isVeggie,
                    isVegan = dish.isVegan,
                    waitingTime = dish.waitingTime,
                    thumbnail = dish.thumbnail,
                    restaurantId = dish.restaurantId,
                    userId = dish.userId,
                    user = userObj
                )
            )
        }
        return result
    }
}
 */

/*
@JvmName("userDishAsDomainModel")
fun Map<DatabaseUser,List<DatabaseDish>>.asDomainModel(): List<UserDish> {
    return map {
        val result = mutableListOf<UserDish>()
        forEach { (user, dishDatabases) ->
            val dishes = dishDatabases.map { dishDb ->
                Dish(
                    id = dishDb.id,
                    name = dishDb.name,
                    price = dishDb.price,
                    newPrice = dishDb.newPrice, // TODO: Should be OldPrice but needs to be consulted with FlutterTeam
                    inOffer = dishDb.inOffer,
                    rating = dishDb.rating,
                    isVeggie = dishDb.isVeggie,
                    isVegan = dishDb.isVegan,
                    waitingTime = dishDb.waitingTime,
                    thumbnail = dishDb.thumbnail,
                    restaurantId = dishDb.restaurantId,
                    userId = dishDb.userId
                )
            }
            
            result.add(
                UserDish(
                    id = user.id,
                    username = user.username,
                    password = user.password,
                    dishes = dishes  // Include the associated list of dishes
                )
            )
        }
        return result
    }
}
 */