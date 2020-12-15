package com.example.codingnabi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class Video(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "url") val url: String,
)
