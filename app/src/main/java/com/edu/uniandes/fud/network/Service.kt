package com.edu.uniandes.fud.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface FudNetService {
    //@GET("restaurants")
    //suspend fun getRestaurantList(): NetworkRestaurantContainer
    companion object {


        
        suspend fun getRestaurantList() : NetworkRestaurantContainer{
            Log.v("XD1","CALLED")
        suspend fun getRestaurantList(): NetworkRestaurantContainer {
            Log.v("XD1", "CALLED")
            val db = Firebase.firestore
            val restaurants: MutableList<NetworkRestaurant> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("Restaurant")
                .get()
                .addOnSuccessListener { restaurantsFirebase ->
                    for (restaurantFb in restaurantsFirebase) {
                        restaurants.add(
                            i,
                            NetworkRestaurant(
                                id = restaurantFb.data["id"].toString().toInt(),
                                name = restaurantFb.data["name"].toString(),
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
                Log.v("XD1", restaurants.toString())
                return NetworkRestaurantContainer(restaurants)
            }

        }


        suspend fun getInteractedRestaurantList(userId: Int) : NetworkRestaurantContainer{
            Log.v("XD1","CALLED")
            val db = Firebase.firestore
            val restaurants : Deferred<NetworkRestaurantContainer> = GlobalScope.async { getRestaurantList() }
            val restaurantsInteracted : MutableList<NetworkRestaurant> = mutableListOf()

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("User_Interact")
                .get()
                .addOnSuccessListener { userInteractions ->
                    GlobalScope.launch {
                        val restaurantsList: List<NetworkRestaurant> = restaurants.await().restaurants
                        for (restaurant in restaurantsList) {
                            var count : Int = 0
                            for (userInteraction in userInteractions){
                                if(restaurant.id == userInteraction.data["restaurant_id"] && userId == userInteraction.data["user_id"])
                                    count +=1
                            }
                            restaurantsInteracted.add(
                                NetworkRestaurant(restaurant.id, restaurant.name, restaurant.location, restaurant.image, count)
                            )
                        }
                        lock.withLock {
                            condition.signal()
                        }
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD2",restaurantsInteracted.toString())

                return NetworkRestaurantContainer(restaurantsInteracted)
            }

        }

        suspend fun getProductList(): NetworkProductContainer {
            val db = Firebase.firestore
            val products: MutableList<NetworkProduct> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("Product")
                .get()
                .addOnSuccessListener { productsFirebase ->
                    for (productFb in productsFirebase) {
                        products.add(
                            i,
                            NetworkProduct(
                                id = productFb.data["id"].toString().toInt(),
                                name = productFb.data["name"].toString(),
                                description = productFb.data["description"].toString(),
                                price = productFb.data["price"].toString().toDouble(),
                                offerPrice = productFb.data["offerPrice"].toString().toDouble(),
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
                Log.v("XD2", products.toString())
                return NetworkProductContainer(products)
            }

        }

        suspend fun getUserList(): NetworkUserContainer {
            val db = Firebase.firestore
            val users: MutableList<NetworkUser> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("User")
                .get()
                .addOnSuccessListener { usersFirebase ->
                    for (userFb in usersFirebase ) {
                        users.add(
                            i,
                            NetworkUser(
                                id = userFb.data["id"].toString().toInt(),
                                username = userFb.data["username"].toString(),
                                name = userFb.data["name"].toString(),
                                number = userFb.data["number"].toString(),
                                password = userFb.data["password"].toString(),
                                documentId = userFb.id
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD2", users.toString())
                return NetworkUserContainer(users)
            }

        }

        suspend fun getFavoritesList(): NetworkFavoriteContainer {
            val db = Firebase.firestore
            val favorites: MutableList<NetworkFavorite> = mutableListOf()
            val i = 0

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            db.collection("Favourites")
                .get()
                .addOnSuccessListener { favorites_firebase ->
                    for (favorite_fb in favorites_firebase) {
                        favorites.add(
                            i,
                            NetworkFavorite(
                                userId = favorite_fb.data["user_id"].toString().toInt(),
                                productId = favorite_fb.data["product_id"].toString().toInt()
                            )
                        )
                    }
                    lock.withLock {
                        condition.signal()
                    }
                }

            lock.withLock {
                condition.await()
                Log.v("XD2", favorites.toString())
                return NetworkFavoriteContainer(favorites)
            }

        }

        suspend fun getRecommendedCriteria(id: Int): String {
            val db = Firebase.firestore

            val lock = ReentrantLock()
            val condition = lock.newCondition()

            val veganSize = suspendCancellableCoroutine<Int> { continuation ->
                db.collection("Filter_Analytics")
                    .whereEqualTo("Vegano", true)
                    .whereEqualTo("idUser", id)
                    .get()
                    .addOnSuccessListener { veganFb ->
                        continuation.resume(veganFb.size())
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }

            val vegetSize = suspendCancellableCoroutine<Int> { continuation ->
                db.collection("Filter_Analytics")
                    .whereEqualTo("Vegetariano", true)
                    .whereEqualTo("idUser", id)
                    .get()
                    .addOnSuccessListener { vegetFb ->
                        continuation.resume(vegetFb.size())
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }

            Log.v("Tamanio vegano", veganSize.toString())
            Log.v("Tamanio veget", vegetSize.toString())
            if (veganSize < vegetSize) {
                return "Vegetariano"
            } else {
                return "Vegano"
            }
        }
    
        suspend fun setUser(id: Int, username: String, name: String, number: String, password: String, documentId: String, context: Context) {

            val db = Firebase.firestore

            val user = hashMapOf(
                "id" to id,
                "username" to username,
                "name" to name,
                "number" to number,
                "password" to password,
                "documentId" to documentId
            )

            db.collection("User")
                .document(documentId)
                .set(user)
                .addOnSuccessListener { Toast.makeText(
                    context,
                    "Usuario Creado Exitosamente",
                    Toast.LENGTH_LONG
                ).show() }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo en la creacion del usuario",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        suspend fun updateUser(id: Int, username: String, name: String, number: String, password: String, documentId: String, context: Context) {

            val db = Firebase.firestore

            val updatedUser = hashMapOf(
                "id" to id,
                "username" to username,
                "name" to name,
                "number" to number,
                "password" to password,
                "documentId" to documentId
            )

            db.collection("User")
                .document(documentId)
                .set(updatedUser)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Usuario Actualizado Exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo en la actualización del usuario",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }


        suspend fun sendFavorite(userId: Int, productId: Int, context: Context){
            val db = Firebase.firestore
            val favoriteMap = hashMapOf(
                "product_id" to productId,
                "user_id" to userId
            )
            db.collection("Favourites")
                .add(favoriteMap)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "¡Añadido a favoritos!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo al agregar a favoritos $e",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        suspend fun sendFilterReport(
            veggie: Boolean,
            vegan: Boolean,
            price: Boolean,
            context: Context
        ) {
            val db = Firebase.firestore
            val report = hashMapOf(
                "Price" to price,
                "Vegano" to vegan,
                "Vegetariano" to veggie,
                "Provider" to "KotlinTeam",
                "Date" to Date().time
            )
            db.collection("Filter_Analytics")
                .add(report)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Reporte (Filter_Analytics) Enviado Exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo en el envío del reporte (Filter_Analytics) $e",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        suspend fun sendFavPromoReport(favourite: Boolean, promotion: Boolean, context: Context) {
            val db = Firebase.firestore
            val report = hashMapOf(
                "favourite" to favourite,
                "promotion" to promotion,
                "Provider" to "KotlinTeam",
                "Date" to Date().time
            )
            db.collection("Fav_Promo_Analytics")
                .add(report)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Reporte (Fav_Promo_Analytics) Enviado Exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo en la creación del usuario",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        suspend fun sendStartingApplicationTime(timeElapsed: Long, now: Long, context: Context) {
            val db = Firebase.firestore
            val report = hashMapOf(
                "time" to timeElapsed,
                "now" to now,
                "provider" to "KotlinTeam",
                "Date" to Date().time
            )

            db.collection("StartingTime")
                .add(report)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Starting Time " + timeElapsed + "ms",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo en el envío del reporte (Starting Time) $e",
                        Toast.LENGTH_LONG
                    ).show()
                }

        }

        fun sendUserRestaurantInteraction(idUser: Int, idRestaurant: Int, context: Context) {
            val db = Firebase.firestore
            val report = hashMapOf(
                "restaurant_id" to idRestaurant,
                "user_id" to idUser,
                "provider" to "KotlinTeam",
            )

            db.collection("User_Interact")
                .add(report)
                .addOnSuccessListener { Toast.makeText(
                    context,
                    "User_Id: $idUser Res_Id $idRestaurant",
                    Toast.LENGTH_SHORT
                )}
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo el envío del reporte (Time Spent) $e",
                        Toast.LENGTH_LONG
                    ).show()
                }

        }
	
		fun sendTimeSpent(duration: Long, screen: String, context: Context) {
            val db = Firebase.firestore
            val report = hashMapOf(
                "time" to duration,
                "screen" to screen,
                "provider" to "KotlinTeam",
            )

            db.collection("Time_Spent_Analytics")
                .add(report)
                .addOnSuccessListener { Toast.makeText(
                    context,
                    "Time spent: "+duration+"ms",
                    Toast.LENGTH_SHORT
                )}
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Fallo el envío del reporte (Time Spent) $e",
                        Toast.LENGTH_LONG
                    ).show()
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