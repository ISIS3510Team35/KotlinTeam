package com.edu.uniandes.fud.network

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface FudNetService {
    //@GET("restaurants")
    //suspend fun getRestaurantList(): NetworkRestaurantContainer
    companion object {
        suspend fun getRestaurantList() : NetworkRestaurantContainer{
            val db = Firebase.firestore
            val restaurants : MutableList<NetworkRestaurant> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("restaurants")
                .get()
                .addOnSuccessListener { restaurants_firebase ->
                    for (restaurant_fb in restaurants_firebase ) {
                        restaurants.add(
                            i,
                            NetworkRestaurant(
                                id = restaurant_fb.data["id"].toString().toInt(),
                                name = restaurant_fb.data["name"].toString(),
                                rating = restaurant_fb.data["rating"].toString().toDouble(),
                                lat = restaurant_fb.data["lat"].toString().toDouble(),
                                long = restaurant_fb.data["long"].toString().toDouble(),
                                thumbnail = restaurant_fb.data["thumbnail"].toString()
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD1",restaurants.toString())
                return NetworkRestaurantContainer(restaurants)
            }

        }

        suspend fun getDishList() : NetworkDishContainer{
            val db = Firebase.firestore
            val dishes : MutableList<NetworkDish> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("dishes")
                .get()
                .addOnSuccessListener { dishes_firebase ->
                    for (dish_fb in dishes_firebase ) {
                        dishes.add(
                            i,
                            NetworkDish(
                                id = dish_fb.data["id"].toString().toInt(),
                                name = dish_fb.data["name"].toString(),
                                price = dish_fb.data["price"].toString().toInt(),
                                newPrice = dish_fb.data["newPrice"].toString().toInt(),
                                inOffer = dish_fb.data["inOffer"].toString().toBoolean(),
                                rating = dish_fb.data["rating"].toString().toDouble(),
                                isVeggie = dish_fb.data["isVeggie"].toString().toBoolean(),
                                isVegan = dish_fb.data["isVegan"].toString().toBoolean(),
                                waitingTime = dish_fb.data["waitingTime"].toString().toInt(),
                                thumbnail = dish_fb.data["thumbnail"].toString(),
                                restaurantId = dish_fb.data["restaurantId"].toString().toInt()
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD2",dishes.toString())
                return NetworkDishContainer(dishes)
            }

        }
    
        suspend fun getUserList() : NetworkUserContainer{
            val db = Firebase.firestore
            val users : MutableList<NetworkUser> = mutableListOf()
            val i = 0
        
            val lock = ReentrantLock()
            val condition = lock.newCondition()
        
            db.collection("users")
                .get()
                .addOnSuccessListener { users_firebase ->
                    for (user_fb in users_firebase ) {
                        users.add(
                            i,
                            NetworkUser(
                                id = user_fb.data["id"].toString().toInt(),
                                username = user_fb.data["username"].toString(),
                                password = user_fb.data["password"].toString()
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }
        
            lock.withLock {
                condition.await()
                Log.v("XD2",users.toString())
                return NetworkUserContainer(users)
            }
        
        }
    }
}

// Single entry point with Firebase does not make much sense
object FudNetwork{

    // Configure retrofit to parse JSON and use coroutines
    /*private val retrofit = Retrofit.Builder()
        .baseUrl("https://fud2-1c029-default-rtdb.firebaseio.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()*/

    //val fud = retrofit.create(FudService::class.java)

    //val fudNetService = FudService.

}