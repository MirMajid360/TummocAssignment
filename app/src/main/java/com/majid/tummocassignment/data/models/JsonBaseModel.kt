package com.majid.tummocassignment.data.models

import com.google.gson.annotations.SerializedName
import com.majid.tummocassignment.data.database.entity.Category

data class JsonBaseModel(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("categories") var categories: ArrayList<Category> = arrayListOf(),

    )
