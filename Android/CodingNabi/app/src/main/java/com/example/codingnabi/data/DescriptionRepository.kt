package com.example.codingnabi.data

import android.content.Context

class DescriptionRepository(private val descriptionDAO: DescriptionDAO) {
    suspend fun getAllDescription() = descriptionDAO.getAllDescription()
    suspend fun getDescriptionById(id: Int) = descriptionDAO.getDescriptionById(id)
}