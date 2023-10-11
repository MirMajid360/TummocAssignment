package com.majid.tummocassignment.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.Item

data class CategoryWithItems(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val items: List<Item>
)
