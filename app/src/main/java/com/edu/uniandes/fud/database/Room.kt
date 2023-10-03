package com.edu.uniandes.fud.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RestaurantDao {
    @Query("select * from databaserestaurant")
    fun getRestaurants(): LiveData<List<DatabaseRestaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(restaurants: List<DatabaseRestaurant>)

}

@Database(entities = [DatabaseRestaurant::class], version = 2, exportSchema = false)
abstract class RestaurantsDatabase: RoomDatabase(){
    abstract val restaurantDao: RestaurantDao
}

private lateinit var INSTANCE: RestaurantsDatabase
fun getDataBase(context: Context): RestaurantsDatabase{
    synchronized(RestaurantsDatabase::class.java){
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                RestaurantsDatabase::class.java,
                "restaurants").build()
        }
    }
    return INSTANCE
}