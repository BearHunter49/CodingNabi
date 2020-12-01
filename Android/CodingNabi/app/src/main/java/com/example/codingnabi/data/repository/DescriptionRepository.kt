package com.example.codingnabi.data.repository

import android.app.Application
import com.example.codingnabi.data.AppDatabase
import com.example.codingnabi.data.DatabaseCopier

class DescriptionRepository(private val application: Application) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(application)
    }

    suspend fun getAllDescription() = database.descriptionDAO().getAllDescription()
    suspend fun getDescriptionById(id: Int) = database.descriptionDAO().getDescriptionById(id)
}