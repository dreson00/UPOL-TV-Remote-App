package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RemoteDao {
    @Query("SELECT * FROM remotes")
    fun getAll(): LiveData<List<Remote>>

    @Insert
    suspend fun insert(remote: Remote): Long

    @Delete
    suspend fun delete(remote: Remote)

}