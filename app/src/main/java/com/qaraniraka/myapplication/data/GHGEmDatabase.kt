package com.qaraniraka.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GHGEmDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: GHGEmDatabase? = null

        fun getDatabase(context: Context): GHGEmDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GHGEmDatabase::class.java, "GHG_Em_database")
                    .build().also { Instance = it }
            }
        }
    }
}