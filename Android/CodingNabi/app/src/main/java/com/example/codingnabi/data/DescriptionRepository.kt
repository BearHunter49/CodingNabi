package com.example.codingnabi.data

import android.app.Application
import android.content.Context

class DescriptionRepository(private val application: Application) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(application)
    }

    suspend fun getAllDescription() = database.descriptionDAO().getAllDescription()
    suspend fun getDescriptionById(id: Int) = database.descriptionDAO().getDescriptionById(id)
}