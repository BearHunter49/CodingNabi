package com.example.codingnabi.data

import android.content.Context

class VideoRepository(private val context: Context) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(context)
    }

    suspend fun getAllVideo() = database.videoDAO().getAllVideo()
    suspend fun getVideoById(id: Int) = database.videoDAO().getVideoById(id)
}