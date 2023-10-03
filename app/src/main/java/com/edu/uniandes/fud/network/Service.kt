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
                                id = 3,
                                name = restaurant_fb.data["name"].toString(),
                                rating = restaurant_fb.data["rating"].toString(),
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