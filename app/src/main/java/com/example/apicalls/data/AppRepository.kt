package com.example.apicalls.data

import com.example.apicalls.data.datamodels.Drink
import com.example.apicalls.data.local.DrinkDatabase
import com.example.apicalls.data.remote.DrinkApi

class AppRepository(private val api: DrinkApi, private val database: DrinkDatabase) {

    val drinkList = database.drinkDatabaseDao.getAll()

    suspend fun getDrinksFromAPI(): List<Drink> {
        return api.retrofitService.getDrinkList().drinks
    }

    suspend fun updateDrinks(drinks: List<Drink>) {
        database.drinkDatabaseDao.updateDrinks(drinks)
    }

    suspend fun insertDrinksToDB(drinks: List<Drink>) {
        database.drinkDatabaseDao.insertAll(drinks)
    }

}


