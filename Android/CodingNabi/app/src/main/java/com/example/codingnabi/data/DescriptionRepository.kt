package com.example.codingnabi.data

import android.content.Context

class DescriptionRepository(private val descriptionDAO: DescriptionDAO) {
    fun getAllDescription() = descriptionDAO.getAllDescription()
    fun getDescriptionById(id: Int) = descriptionDAO.getDescriptionById(id)
}