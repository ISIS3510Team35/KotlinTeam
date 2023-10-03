package com.edu.uniandes.fud.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {
    
    //Restaurant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRestaurants(restaurants: List<DatabaseRestaurant>) : LongArray
    
    @Query("SELECT * FROM DatabaseRestaurant")
    fun getRestaurants(): LiveData<List<DatabaseRestaurant>>
    
    //Restaurant-List<Dish>
    @Query("SELECT * FROM DatabaseRestaurant JOIN DatabaseDish ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getRestaurantsDishes() : LiveData<Map<DatabaseRestaurant,List<DatabaseDish>>>
    


    //Dish
    @Query("SELECT * FROM DatabaseDish")
    fun getDishes(): LiveData<List<DatabaseDish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDishes(dishes: List<DatabaseDish>) : LongArray
    
    //Dish-List<Restaurant>
    @Query("SELECT * FROM DatabaseDish JOIN DatabaseRestaurant ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getDishesRestaurant() : LiveData<Map<DatabaseDish,DatabaseRestaurant>>
    
    /*
    //Dish-List<User>
    @Query("SELECT * FROM DatabaseDish JOIN DatabaseUser ON DatabaseUser.id = DatabaseDish.userId")
    fun getDishesUser() : LiveData<Map<DatabaseDish,DatabaseUser>>
     */
    
    //Dish-Restaurant
    @Transaction
    fun insertRestaurantsAndDishes(dishes: List<DatabaseDish>, restaurants: List<DatabaseRestaurant>){
        insertAllRestaurants(restaurants)
        insertAllDishes(dishes)
    }
    
    //User
    @Query("SELECT * FROM DatabaseUser")
    fun getUsers(): LiveData<List<DatabaseUser>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<DatabaseUser>) : LongArray
    
    /*
    //User-List<Dish>
    @Query("SELECT * FROM DatabaseUser JOIN DatabaseDish ON DatabaseUser.id = DatabaseDish.userId")
    fun getUsersDishes() : LiveData<Map<DatabaseUser,List<DatabaseDish>>>
     */
    


}

@Database(entities = [DatabaseRestaurant::class, DatabaseDish::class, DatabaseUser::class], version = 6)
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


