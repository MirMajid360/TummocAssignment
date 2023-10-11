package com.majid.tummocassignment.data.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.CategoryWithItems
import com.majid.tummocassignment.data.database.entity.Favourite

@Dao
interface TummocDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categories: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(categories: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToFavourite(favourite: Favourite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(cart: Cart)

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM favourite")
    suspend fun getFavourites(): List<Favourite>

    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<Cart>

    @Update
    suspend fun updateCart(cart: Cart)
    @Delete
    suspend fun removeItemFromFavourite(favourite: Favourite)

    @Delete
    suspend fun removeItemFromCart(cart: Cart)

//
//    @Query("SELECT * FROM item  WHERE  categoryId = :categoryId")
//    suspend  fun getItems(categoryId :Int):List<Item>

    @Transaction
    @Query("SELECT * FROM Category")
    suspend fun getCategoriesWithItems(): List<CategoryWithItems>
}