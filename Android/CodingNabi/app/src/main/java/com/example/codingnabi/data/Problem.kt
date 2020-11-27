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
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Video::class,
            parentColumns = ["id"],
            childColumns = ["video_id"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Problem(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "available_blocks") val usableBlocks: String,
    @ColumnInfo(name = "description_id") val descriptionId: Int,
    @ColumnInfo(name = "video_id") val videoId: Int
)
