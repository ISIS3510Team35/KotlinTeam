package com.edu.uniandes.fud.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM DatabaseRestaurant")
    fun getRestaurants(): LiveData<List<DatabaseRestaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRestaurants(restaurants: List<DatabaseRestaurant>)

    @Query("SELECT * FROM DatabaseDish")
    fun getDishes(): LiveData<List<DatabaseDish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDishes(dishes: List<DatabaseDish>)

    @Query(
        "SELECT * FROM DatabaseRestaurant JOIN DatabaseDish ON DatabaseRestaurant.id = DatabaseDish.restaurantId"
    )
    fun loadRestaurantsAndDishes() : LiveData<Map<DatabaseRestaurant,List<DatabaseDish>>>

}

@Database(entities = [DatabaseRestaurant::class, DatabaseDish::class], version = 3, exportSchema = false)
abstract class DatabaseRoom: RoomDatabase(){
    abstract val databaseDao: DatabaseDao
}

private lateinit var INSTANCE: DatabaseRoom
fun getDataBase(context: Context): DatabaseRoom{
    synchronized(DatabaseRoom::class.java){
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                DatabaseRoom::class.java,
                "restaurants").build()
        }
    }
    return INSTANCE
}