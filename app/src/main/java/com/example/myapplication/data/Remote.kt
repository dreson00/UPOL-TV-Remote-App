package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remotes")
data class Remote(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "remote_name") var name: String,
    @ColumnInfo(name = "brand_id") var brand_id: Int
) : java.io.Serializable