package com.example.codingnabi.data

class VideoRepository(private val videoDAO: VideoDAO) {
    fun getAllVideo() = videoDAO.getAllVideo()
    fun getVideoById(id: Int) = videoDAO.getVideoById(id)
}