package com.example.codingnabi.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.codingnabi.data.entity.Video

@Dao
interface VideoDAO {
    @Query("SELECT * FROM video")
    fun getAllVideo(): List<Video>

    @Query("SELECT * FROM video WHERE id = :id")
    fun getVideoById(id: Int): Video
}