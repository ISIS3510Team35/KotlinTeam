package com.edu.uniandes.fud.database

import android.app.Application
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.edu.uniandes.fud.FuDApplication
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
    
    //Restaurant-List<Product>
    @Query("SELECT * FROM DatabaseRestaurant JOIN DatabaseProduct ON DatabaseRestaurant.id = DatabaseProduct.restaurantId")
    fun getRestaurantsProducts() : Flow<Map<DatabaseRestaurant, List<DatabaseProduct>>>
    


    //Product
    @Query("SELECT * FROM DatabaseProduct")
    fun getProducts(): Flow<List<DatabaseProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(products: List<DatabaseProduct>) : LongArray


    
    //Product-List<Restaurant>
    @Query("SELECT * FROM DatabaseProduct JOIN DatabaseRestaurant ON DatabaseRestaurant.id = DatabaseProduct.restaurantId")
    fun getProductsRestaurant() : Flow<Map<DatabaseProduct,DatabaseRestaurant>>
    
    /*
    //Product-List<User>
    @Query("SELECT * FROM DatabaseProduct JOIN DatabaseUser ON DatabaseUser.id = DatabaseProduct.userId")
    fun getProductsUser() : LiveData<Map<DatabaseProduct,DatabaseUser>>
     */
    
    //Product-Restaurant
    @Transaction
    fun insertRestaurantsAndProductsAndUserAndFavorites(products: List<DatabaseProduct>, restaurants: List<DatabaseRestaurant>, users: List<DatabaseUser>, favorites: List<DatabaseFavorite>){
        insertAllRestaurants(restaurants)
        insertAllProducts(products)
        insertAllUsers(users)
        insertAllFavorites(favorites)
    }
    
    //User
    @Query("SELECT * FROM DatabaseUser")
    fun getUsers(): Flow<List<DatabaseUser>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<DatabaseUser>) : LongArray
    
    
    /*
    //User-List<Product>
    @Query("SELECT * FROM DatabaseUser JOIN DatabaseProduct ON DatabaseUser.id = DatabaseProduct.userId")
    fun getUsersProducts() : LiveData<Map<DatabaseUser,List<DatabaseProduct>>>
     */

    // Favorite
    @Query("SELECT * FROM DatabaseFavorite")
    fun getFavorites(): Flow<List<DatabaseFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavorites(favorites: List<DatabaseFavorite>) : LongArray


}

@Database(entities = arrayOf(DatabaseRestaurant::class,DatabaseProduct::class,DatabaseUser::class,DatabaseFavorite::class), version = 7)
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
            DBRepository(database).refreshRestaurantInteractedData(FuDApplication.getIdUser())
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



