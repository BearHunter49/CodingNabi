package com.example.codingnabi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "problem", foreignKeys = [ForeignKey(
        entity = Description::class,
        parentColumns = ["id"],
        childColumns = ["description_id"],
    ),
        ForeignKey(
            entity = Video::class,
            parentColumns = ["id"],
            childColumns = ["video_id"],
        )],
    primaryKeys = ["category", "level"]
)
data class Problem(
    val category: String,
    val level: Int,
    @ColumnInfo(name = "available_blocks") val usableBlocks: String,
    @ColumnInfo(name = "description_id") val descriptionId: Int,
    @ColumnInfo(name = "video_id") val videoId: Int,
    @ColumnInfo(name = "is_cleared") val isCleared: Int
)
