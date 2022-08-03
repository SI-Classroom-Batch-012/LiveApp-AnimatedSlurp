package com.example.apicalls.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apicalls.data.datamodels.Drink

@Database(entities = [Drink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {

    abstract val drinkDatabaseDao: DrinkDatabaseDao
}

private lateinit var INSTANCE: DrinkDatabase

// if there's no Database a new one is built
fun getDatabase(context: Context): DrinkDatabase {
    synchronized(DrinkDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DrinkDatabase::class.java,
                "drink_database"
            )
                .build()
        }
    }
    return INSTANCE
}

