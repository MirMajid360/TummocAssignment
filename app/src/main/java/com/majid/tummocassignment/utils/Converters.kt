package com.majid.tummocassignment.utils

import androidx.room.TypeConverter
import com.majid.tummocassignment.data.database.entity.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    @TypeConverter
    fun fromJson(json: String): ArrayList<Item> {
        val listType = object : TypeToken<ArrayList<Item>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(items: ArrayList<Item>): String {
        return Gson().toJson(items)
    }

    fun formatPrice(price: Double): String {
        return String.format("%.2f", price)
    }
}
