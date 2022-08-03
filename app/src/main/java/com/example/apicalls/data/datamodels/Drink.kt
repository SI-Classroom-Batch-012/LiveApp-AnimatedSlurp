package com.example.apicalls.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Drink(

    @PrimaryKey
    @Json(name = "idDrink")
    val id: Long,

    @Json(name = "strDrink")
    val name: String,

    @Json(name = "strDrinkThumb")
    val picture: String,

    var clicked: Boolean = false
)
