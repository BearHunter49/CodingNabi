package com.example.codingnabi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "description")
data class Description(@PrimaryKey val id: Int,
@ColumnInfo(name = "goal") val goal: String,
@ColumnInfo(name = "hint") val hint: String,
@ColumnInfo(name = "answer") val answer: String)
