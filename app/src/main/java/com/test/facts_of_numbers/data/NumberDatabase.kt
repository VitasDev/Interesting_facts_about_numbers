package com.test.facts_of_numbers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.facts_of_numbers.model.Numbers

@Database(entities = [Numbers::class], version = 1, exportSchema = false)
abstract class NumberDatabase: RoomDatabase() {

    abstract fun numberDao(): NumberDao

    companion object {
        @Volatile
        private var INSTANCE: NumberDatabase? = null

        fun getDatabase(context: Context): NumberDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NumberDatabase::class.java,
                    "number_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}