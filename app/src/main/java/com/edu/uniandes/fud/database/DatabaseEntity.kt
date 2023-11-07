package com.edu.uniandes.fud.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edu.uniandes.fud.domain.Favorite
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.domain.User
import com.google.firebase.firestore.GeoPoint

@Entity
data class DatabaseRestaurant(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var image: String = ""
)

@Entity
data class DatabaseProduct(
	@PrimaryKey
	var id: Int = 0,
	var name: String = "",
	var description: String = "",
	var price: Double = 0.0,
	var offerPrice: Double = 0.0,
	var inOffer: Boolean = false,
	var rating: Double = 0.0,
	var type: String = "",
	var category: String = "",
	var image: String = "",
	var restaurantId: Int = 0
)

@Entity
data class DatabaseUser(
    @PrimaryKey
    var id: Int = 0,
    var username: String = "",
    var password: String = ""
)

@Entity(primaryKeys = ["userId", "productId"])
data class DatabaseFavorite(
    val userId: Int = 0,
    val productId: Int = 0
)

// TODO: In the future add new dataClass for offers and prices independent to products

@JvmName("restaurantAsDomainModel")
fun List<DatabaseRestaurant>.asDomainModel(): List<Restaurant> {
    return map {
        Restaurant(
            id = it.id,
            name = it.name,
            location = GeoPoint(it.latitude, it.longitude),
            image = it.image)
    }
}

@JvmName("productAsDomainModel")
fun List<DatabaseProduct>.asDomainModel(): List<Product> {
    return map {
        Product(
            id = it.id,
            name = it.name,
            description = it.description,
            price = it.price,
            offerPrice = it.price,
            inOffer = it.inOffer,
            rating = it.rating,
            type = it.type,
            category = it.category,
            image = it.image,
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

@JvmName("restaurantProductAsDomainModel")
fun Map<DatabaseRestaurant,List<DatabaseProduct>>.asDomainModel(): List<RestaurantProduct> {
    return map {
        val result = mutableListOf<RestaurantProduct>()
        forEach { (restaurant, productDatabases) ->
            val products = productDatabases.map { productDb ->
                Product(
                    id = productDb.id,
                    name = productDb.name,
                    description = productDb.description,
                    price = productDb.price,
                    offerPrice = productDb.offerPrice,
                    inOffer = productDb.inOffer,
                    rating = productDb.rating,
                    type = productDb.type,
                    category = productDb.category,
                    image = productDb.image,
                    restaurantId = productDb.restaurantId
                )
            }

            result.add(
                RestaurantProduct(
                    id = restaurant.id,
                    name = restaurant.name,
                    location = GeoPoint(restaurant.latitude, restaurant.longitude),
                    image = restaurant.image,
                    products = products  // Include the associated list of products
                )
            )
        }
        return result
    }
}

@JvmName("productRestaurantAsDomainModel")
fun Map<DatabaseProduct, DatabaseRestaurant>.asDomainModel(): List<ProductRestaurant> {
    return map {
        val result = mutableListOf<ProductRestaurant>()
        forEach { (product, restaurant) ->
            val restaurantObj = Restaurant(
                id = restaurant.id,
                name = restaurant.name,
                location = GeoPoint(restaurant.latitude, restaurant.longitude),
                image = restaurant.image
            )

            result.add(
                ProductRestaurant(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    offerPrice = product.offerPrice,
                    inOffer = product.inOffer,
                    rating = product.rating,
                    type = product.type,
                    category = product.category,
                    image = product.image,
                    restaurantId = product.restaurantId,
                    restaurant = restaurantObj
                )
            )
        }
        return result
    }
}


@JvmName("favoriteAsDomainModel")
fun List<DatabaseFavorite>.asDomainModel(): List<Favorite> {
    return map {
        Favorite(
            userId = it.userId,
            productId = it.productId
        )
    }
}

/*
@JvmName("productUserAsDomainModel")
fun Map<DatabaseProduct, DatabaseUser>.asDomainModel(): List<ProductUser> {
    return map {
        val result = mutableListOf<ProductUser>()
        forEach { (product, user) ->
            val userObj = User(
                id = user.id,
                username = user.username,
                password = user.password
            )
            
            result.add(
                ProductUser(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    offerPrice = product.offerPrice,
                    inOffer = product.inOffer,
                    rating = product.rating,
                    type = product.type,
                    category = product.category,
                    image = product.image,
                    restaurantId = product.restaurantId,
                    userId = product.userId,
                    user = userObj
                )
            )
        }
        return result
    }
}
 */

/*
@JvmName("userProductAsDomainModel")
fun Map<DatabaseUser,List<DatabaseProduct>>.asDomainModel(): List<UserProduct> {
    return map {
        val result = mutableListOf<UserProduct>()
        forEach { (user, productDatabases) ->
            val products = productDatabases.map { productDb ->
                Product(
                    id = productDb.id,
                    name = productDb.name,
                    description = productDb.description,
                    price = productDb.price,
                    offerPrice = productDb.offerPrice,
                    inOffer = productDb.inOffer,
                    rating = productDb.rating,
                    type = productDb.type,
                    category = productDb.category,
                    image = productDb.image,
                    restaurantId = productDb.restaurantId,
                    userId = productDb.userId
                )
            }
            
            result.add(
                UserProduct(
                    id = user.id,
                    username = user.username,
                    password = user.password,
                    products = products  // Include the associated list of products
                )
            )
        }
        return result
    }
}
 */