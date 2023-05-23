package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RemoteButtonDao {
    @Query("SELECT * FROM remote_buttons")
    fun getAll(): List<RemoteButton>

    @Query("SELECT * FROM remote_buttons WHERE brand_id = :brandId")
    fun getButtonsByBrandId(brandId: Int): List<RemoteButton>
}