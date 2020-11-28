package com.example.codingnabi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class Video(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "top") val topViewUrl: String,
    @ColumnInfo(name = "side") val sideViewUrl: String
)
