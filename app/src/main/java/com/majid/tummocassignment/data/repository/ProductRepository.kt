package com.majid.tummocassignment.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.majid.tummocassignment.app.App

import com.majid.tummocassignment.data.database.TummocDB
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.CategoryWithItems
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.data.models.JsonBaseModel
import com.majid.tummocassignment.utils.ReadJSONFromAssets
import javax.inject.Inject

class ProductRepository @Inject constructor(private val tummocDB: TummocDB) {
    var dataChanged: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    suspend fun getProducts() {
        try {
            val jsonString = ReadJSONFromAssets(App.context, "shopping.json")
            val data = Gson().fromJson(jsonString, JsonBaseModel::class.java)


            for (c in data.categories) {
                tummocDB.getTummocDAO().addCategory(c)
                for (i in c.items) {
                    i.categoryId = c.id
                    tummocDB.getTummocDAO().addItem(i)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateItem(item: Item) {

        tummocDB.getTummocDAO().updateItem(item)
        dataChanged.postValue(true)
    }
    suspend fun updateCart(cart: Cart) {

        tummocDB.getTummocDAO().updateCart(cart)
        dataChanged.postValue(true)
    }

    suspend fun getCategoriesWithItems(): List<CategoryWithItems> {

        return tummocDB.getTummocDAO().getCategoriesWithItems()

    }

    suspend fun getFavouriteItems(): List<Favourite> {

        return tummocDB.getTummocDAO().getFavourites()

    }

    suspend fun getCartItems(): List<Cart> {

        return tummocDB.getTummocDAO().getCartItems()

    }

    suspend fun addItemToFavourite(favourite: Favourite) {
        tummocDB.getTummocDAO().addItemToFavourite(favourite)
        dataChanged.postValue(true)
    }

    suspend fun addItemToCart(cart: Cart) {
        tummocDB.getTummocDAO().addItemToCart(cart)
        dataChanged.postValue(true)
    }

    suspend fun removeFavourite(favourite: Favourite) {
        tummocDB.getTummocDAO().removeItemFromFavourite(favourite)
        dataChanged.postValue(true)
    }

    suspend fun removeItemFromCart(cart: Cart) {
        tummocDB.getTummocDAO().removeItemFromCart(cart)
        dataChanged.postValue(true)
    }


//    suspend fun getItems(id:Int):List<Item>{
//
//
//        return  tummocDB.getTummocDAO().getItems(id)
//
//
//    }
}
