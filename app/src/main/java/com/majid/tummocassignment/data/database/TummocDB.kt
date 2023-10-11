package com.majid.tummocassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.utils.Converters

@Database(entities = [Category::class, Item::class,Favourite::class,Cart::class], version = 2)
@TypeConverters(Converters::class)
abstract class TummocDB : RoomDatabase() {

    abstract fun getTummocDAO(): TummocDAO
}