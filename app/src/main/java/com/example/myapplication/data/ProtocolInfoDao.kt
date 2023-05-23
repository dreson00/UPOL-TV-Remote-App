package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProtocolInfoDao {
    @Query("SELECT * FROM protocol_info WHERE brand_id = :brandId")
    fun getProtocolInfoByBrandId(brandId: Int): ProtocolInfo
}