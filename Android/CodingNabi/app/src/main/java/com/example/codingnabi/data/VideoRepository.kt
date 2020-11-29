package com.example.codingnabi.data

import android.app.Application
import android.content.Context

class VideoRepository(private val application: Application) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(application)
    }

    suspend fun getAllVideo() = database.videoDAO().getAllVideo()
    suspend fun getVideoById(id: Int) = database.videoDAO().getVideoById(id)
}