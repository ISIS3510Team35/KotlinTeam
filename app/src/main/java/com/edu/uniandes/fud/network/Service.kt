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

            db.collection("Restaurant")
                .get()
                .addOnSuccessListener { restaurantsFirebase ->
                    for (restaurantFb in restaurantsFirebase ) {
                        restaurants.add(
                            i,
                            NetworkRestaurant(
                                id = restaurantFb.data["id"].toString().toInt(),
                                name = restaurantFb.data["name"].toString(),
                                rating = restaurantFb.data["rating"].toString().toDouble(),
                                location = restaurantFb.data["location"] as com.google.firebase.firestore.GeoPoint,
                                image = restaurantFb.data["image"].toString()
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

        suspend fun getProductList() : NetworkProductContainer{
            val db = Firebase.firestore
            val products : MutableList<NetworkProduct> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("Product")
                .get()
                .addOnSuccessListener { productsFirebase ->
                    for (productFb in productsFirebase ) {
                        products.add(
                            i,
                            NetworkProduct(
                                id = productFb.data["id"].toString().toInt(),
                                name = productFb.data["name"].toString(),
                                description = productFb.data["description"].toString(),
                                price = productFb.data["price"].toString().toInt(),
                                offerPrice = productFb.data["offerPrice"].toString().toInt(),
                                inOffer = productFb.data["inOffer"].toString().toBoolean(),
                                rating = productFb.data["rating"].toString().toDouble(),
                                type = productFb.data["type"].toString(),
                                category = productFb.data["category"].toString(),
                                image = productFb.data["image"].toString(),
                                restaurantId = productFb.data["restaurantId"].toString().toInt()
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD2",products.toString())
                return NetworkProductContainer(products)
            }

        }
    
        suspend fun getUserList() : NetworkUserContainer{
            val db = Firebase.firestore
            val users : MutableList<NetworkUser> = mutableListOf()
            val i = 0
        
            val lock = ReentrantLock()
            val condition = lock.newCondition()
        
            db.collection("User")
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
    
        suspend fun setUser(id: Int, username: String, password: String) {

            val db = Firebase.firestore

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            val user = hashMapOf(
                "id" to id,
                "username" to username,
                "password" to password
            )

            db.collection("User")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("XD", "DocumentSnapshot added with ID: ${documentReference.id}")
                    lock.withLock {
                        condition.signal()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("XD", "Error adding document", e)
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
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