package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RemoteBrand::class, RemoteButton::class, ProtocolInfo::class], version = 9)
abstract class RemoteTransmissionDataDatabase : RoomDatabase() {
    abstract fun getRemoteBrandDao(): RemoteBrandDao
    abstract fun getRemoteButtonDao(): RemoteButtonDao
    abstract fun getProtocolInfoDao(): ProtocolInfoDao

    companion object {

        private const val DATABASE_NAME = "remote_transmission_data"

        @Volatile
        private var INSTANCE: RemoteTransmissionDataDatabase? = null
        fun getDatabase(context: Context): RemoteTransmissionDataDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RemoteTransmissionDataDatabase::class.java,
                    DATABASE_NAME
                ).createFromAsset("$DATABASE_NAME.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}