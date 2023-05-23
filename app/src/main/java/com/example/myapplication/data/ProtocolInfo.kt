package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "protocol_info")
data class ProtocolInfo(
    @ColumnInfo(name = "protocol_id") @PrimaryKey val protocol_id: Int,
    @ColumnInfo(name = "brand_id") val brand_id: Int,
    @ColumnInfo(name = "hdr_mark") val hdr_mark: Int,
    @ColumnInfo(name = "hdr_space") val hdr_space: Int,
    @ColumnInfo(name = "bit_mark") val bit_mark: Int,
    @ColumnInfo(name = "one_space") val one_space: Int,
    @ColumnInfo(name = "zero_space") val zero_space: Int
)