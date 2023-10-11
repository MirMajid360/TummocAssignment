package com.majid.tummocassignment.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Category(
   @PrimaryKey(autoGenerate = false)
   var id: Int,
   var name: String,
   @Ignore
   var items: ArrayList<Item> = arrayListOf(),


   ){
   // Add an empty constructor
   constructor() : this(0, "")
}