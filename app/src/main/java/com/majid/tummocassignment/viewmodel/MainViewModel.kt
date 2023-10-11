package com.majid.tummocassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.CategoryWithItems
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {

    val categoriesLiveData: MutableLiveData<List<CategoryWithItems>> =
        MutableLiveData<List<CategoryWithItems>>()

    val favouriteLiveData: MutableLiveData<List<Favourite>> =
        MutableLiveData<List<Favourite>>()
    val cartLiveData: MutableLiveData<List<Cart>> =
        MutableLiveData<List<Cart>>()


    val dataChanged : MutableLiveData<Boolean>
      get() = repository.dataChanged

    fun saveDate(){
        viewModelScope.launch {
            repository.getProducts()
        }
    }

    fun getCategoryDate(){
        viewModelScope.launch {
            try {
                categoriesLiveData.value = repository.getCategoriesWithItems()
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    fun getFavourite() {
        viewModelScope.launch {
            try {
                favouriteLiveData.value = repository.getFavouriteItems()
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }
    fun updateItem(item: Item) {
        viewModelScope.launch {
            try {
                repository.updateItem(item)
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.updateCart(cart)
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            try {
                cartLiveData.value = repository.getCartItems()
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    fun addItemToCart(cart: Cart) {

        viewModelScope.launch {
            repository.addItemToCart(cart)

        }
    }

    fun addItemToFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.addItemToFavourite(favourite)
        }

    }

    fun removeItemFromFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.removeFavourite(favourite)
        }
    }

    fun removeItemFromCart(cart: Cart) {
        viewModelScope.launch {
            repository.removeItemFromCart(cart)
        }
    }

//    fun getItems(id:Int) : ArrayList<Item>{
//        val list = ArrayList<Item>()
//        viewModelScope.launch {
//          list.addAll(repository.getItems(id))
//        }
//
//        return  list
//    }


}
