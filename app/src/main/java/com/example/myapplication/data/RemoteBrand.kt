package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brands")
data class RemoteBrand(
    @PrimaryKey @ColumnInfo(name = "brand_id") val brand_id: Int,
    @ColumnInfo(name = "brand_name") var brand_name: String
) : java.io.Serializable
