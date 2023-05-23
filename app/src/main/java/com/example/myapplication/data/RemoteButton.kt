package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_buttons")
class RemoteButton(
    @PrimaryKey @ColumnInfo(name = "button_id") val button_id: Int,
    @ColumnInfo(name = "button_name") val button_name: String,
    @ColumnInfo(name = "command") val command: String,
    @ColumnInfo(name = "command_format") val command_format: String,
    @ColumnInfo(name = "bits") val bits: Int? = 32,
    @ColumnInfo(name = "brand_id") val brand_id: Int
) : java.io.Serializable
