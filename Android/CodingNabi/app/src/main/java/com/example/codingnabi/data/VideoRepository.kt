package com.example.codingnabi.data

class VideoRepository(private val videoDAO: VideoDAO) {
    suspend fun getAllVideo() = videoDAO.getAllVideo()
    suspend fun getVideoById(id: Int) = videoDAO.getVideoById(id)
}