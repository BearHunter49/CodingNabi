package com.example.codingnabi.data

import android.content.Context

class DescriptionRepository(private val context: Context) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(context)
    }

    suspend fun getAllDescription() = database.descriptionDAO().getAllDescription()
    suspend fun getDescriptionById(id: Int) = database.descriptionDAO().getDescriptionById(id)
}