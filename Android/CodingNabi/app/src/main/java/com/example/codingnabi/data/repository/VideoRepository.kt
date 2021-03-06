package com.example.codingnabi.data.repository

import android.app.Application
import com.example.codingnabi.data.AppDatabase
import com.example.codingnabi.data.DatabaseCopier

class VideoRepository(private val application: Application) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(application)
    }

    suspend fun getAllVideo() = database.videoDAO().getAllVideo()
    suspend fun getVideoById(id: Int) = database.videoDAO().getVideoById(id)
}