package com.edu.uniandes.fud.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM DatabaseRestaurant")
    fun getRestaurants(): LiveData<List<DatabaseRestaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRestaurants(restaurants: List<DatabaseRestaurant>) : LongArray

    @Query("SELECT * FROM DatabaseDish")
    fun getDishes(): LiveData<List<DatabaseDish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDishes(dishes: List<DatabaseDish>) : LongArray

    @Query("SELECT * FROM DatabaseRestaurant JOIN DatabaseDish ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getRestaurantsDishes() : LiveData<Map<DatabaseRestaurant,List<DatabaseDish>>>

    @Query("SELECT * FROM DatabaseDish JOIN DatabaseRestaurant ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getDishesRestaurant() : LiveData<Map<DatabaseDish,DatabaseRestaurant>>

}

@Database(entities = [DatabaseRestaurant::class, DatabaseDish::class], version = 5)
abstract class DatabaseRoom: RoomDatabase(){
    abstract val databaseDao: DatabaseDao
}

private lateinit var INSTANCE: DatabaseRoom
fun getDataBase(context: Application): DatabaseRoom{
    synchronized(DatabaseRoom::class.java){
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context,
                DatabaseRoom::class.java,
                "database_room.db")
                .allowMainThreadQueries()
                .build()
        }
    }
    return INSTANCE
}