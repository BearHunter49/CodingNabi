package com.example.codingnabi.data

import androidx.room.ColumnInfo

data class CategoryAndLevel(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "level") val level: Int
)
