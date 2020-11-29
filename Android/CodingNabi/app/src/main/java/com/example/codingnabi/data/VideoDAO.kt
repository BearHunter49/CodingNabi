package com.example.codingnabi.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface VideoDAO {
    @Query("SELECT * FROM video")
    fun getAllVideo(): List<Video>

    @Query("SELECT * FROM video WHERE id = :id")
    fun getVideoById(id: Int): Video
}