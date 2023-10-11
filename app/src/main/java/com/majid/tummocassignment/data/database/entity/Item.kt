package com.majid.tummocassignment.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
  tableName = "item",
  foreignKeys = [
    ForeignKey(
      entity = Category::class,
      parentColumns = ["id"],
      childColumns = ["categoryId"],
      onDelete = ForeignKey.CASCADE // Define the desired onDelete behavior
    )
  ]
)
data class Item(

  @PrimaryKey
  var id: Int? = null,
  var categoryId: Int? = null,
  var name: String? = null,
  var icon: String? = null,
  var price: Double? = null,
  var quantity : Int = 0,
  var isFavourite: Boolean = false,

  ){
  // Add an empty constructor
  constructor() : this(0,0,"","",0.0,0,false)
}