package com.example.apicalls.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apicalls.data.datamodels.Drink

@Dao
interface DrinkDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(drinks: List<Drink>)

    @Query("SELECT * from Drink")
    fun getAll(): LiveData<List<Drink>>

    @Update
    suspend fun updateDrinks(drinks: List<Drink>)

    @Query("DELETE from Drink")
    fun deleteAll()
}
