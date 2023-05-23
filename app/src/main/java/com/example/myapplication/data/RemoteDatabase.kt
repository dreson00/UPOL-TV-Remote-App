package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Remote::class], version = 3)
abstract class RemoteDatabase : RoomDatabase() {
    abstract fun getRemoteDao(): RemoteDao

    companion object {

        @Volatile
        private var INSTANCE: RemoteDatabase? = null
        fun getDatabase(context: Context): RemoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RemoteDatabase::class.java,
                    "MyRemoteDatabase"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                instance
            }
        }
    }
}