package com.edu.uniandes.fud.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Dao
interface DatabaseDao {
    
    //Restaurant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRestaurants(restaurants: List<DatabaseRestaurant>) : LongArray
    
    @Query("SELECT * FROM DatabaseRestaurant")
    fun getRestaurants(): Flow<List<DatabaseRestaurant>>
    
    //Restaurant-List<Dish>
    @Query("SELECT * FROM DatabaseRestaurant JOIN DatabaseDish ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getRestaurantsDishes() : Flow<Map<DatabaseRestaurant, List<DatabaseDish>>>
    


    //Dish
    @Query("SELECT * FROM DatabaseDish")
    fun getDishes(): Flow<List<DatabaseDish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDishes(dishes: List<DatabaseDish>) : LongArray
    
    //Dish-List<Restaurant>
    @Query("SELECT * FROM DatabaseDish JOIN DatabaseRestaurant ON DatabaseRestaurant.id = DatabaseDish.restaurantId")
    fun getDishesRestaurant() : Flow<Map<DatabaseDish,DatabaseRestaurant>>
    
    /*
    //Dish-List<User>
    @Query("SELECT * FROM DatabaseDish JOIN DatabaseUser ON DatabaseUser.id = DatabaseDish.userId")
    fun getDishesUser() : LiveData<Map<DatabaseDish,DatabaseUser>>
     */
    
    //Dish-Restaurant
    @Transaction
    fun insertRestaurantsAndDishesAndUser(dishes: List<DatabaseDish>, restaurants: List<DatabaseRestaurant>, users: List<DatabaseUser>){
        insertAllRestaurants(restaurants)
        insertAllDishes(dishes)
        insertAllUsers(users)
    }
    
    //User
    @Query("SELECT * FROM DatabaseUser")
    fun getUsers(): Flow<List<DatabaseUser>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<DatabaseUser>) : LongArray
    
    /*
    //User-List<Dish>
    @Query("SELECT * FROM DatabaseUser JOIN DatabaseDish ON DatabaseUser.id = DatabaseDish.userId")
    fun getUsersDishes() : LiveData<Map<DatabaseUser,List<DatabaseDish>>>
     */
    


}

@Database(entities = arrayOf(DatabaseRestaurant::class,DatabaseDish::class,DatabaseUser::class), version = 6)
abstract class DatabaseRoom: RoomDatabase(){
    abstract fun databaseDao(): DatabaseDao
    private class RoomDatabaseCallback( private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database)
                }
            }
        }
        suspend fun populateDatabase(database: DatabaseRoom) {
            DBRepository(database).refreshData()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DatabaseRoom ? = null
        fun getDataBase(context: Application, scope: CoroutineScope): DatabaseRoom {
            return INSTANCE ?: synchronized(this){
                    val instance = Room.databaseBuilder(context,
                        DatabaseRoom::class.java,
                        "database_room.db")
                        .allowMainThreadQueries()
                        .addCallback(RoomDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    return instance
            }
        }
    }
}



