package com.example.apicalls.data

import androidx.lifecycle.LiveData
import com.example.apicalls.data.datamodels.Drink
import com.example.apicalls.data.local.DrinkDatabase
import com.example.apicalls.data.remote.DrinkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "AppRepository"

class AppRepository(private val api: DrinkApi, private val database: DrinkDatabase) {

    val drinkList: LiveData<List<Drink>> = database.drinkDatabaseDao.getAll()

    suspend fun getDrinks() {
        withContext(Dispatchers.IO) {
            val newDrinkList = api.retrofitService.getDrinkList().drinks
            database.drinkDatabaseDao.insertAll(newDrinkList)
        }
    }

    suspend fun updateDrinks(drinks: List<Drink>) {
        withContext(Dispatchers.IO) {
            database.drinkDatabaseDao.updateDrinks(drinks)
        }
    }
}


