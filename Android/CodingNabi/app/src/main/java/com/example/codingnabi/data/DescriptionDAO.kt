package com.example.codingnabi.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DescriptionDAO {
    @Query("SELECT * FROM description")
    fun getAllDescription(): List<Description>

    @Query("SELECT * FROM description WHERE id = :id")
    fun getDescriptionById(id: Int): Description
}