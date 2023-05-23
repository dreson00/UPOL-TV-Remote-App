package com.example.myapplication.data

import androidx.room.*

@Dao
interface RemoteBrandDao {
    @Query("SELECT * FROM brands")
    fun getAll(): List<RemoteBrand>

    @Query("SELECT * FROM brands WHERE brand_id = :id")
    fun findById(id: Int): RemoteBrand
}