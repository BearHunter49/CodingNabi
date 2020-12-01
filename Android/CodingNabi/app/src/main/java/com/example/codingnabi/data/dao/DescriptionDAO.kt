package com.example.codingnabi.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.codingnabi.data.entity.Description

@Dao
interface DescriptionDAO {
    @Query("SELECT * FROM description")
    fun getAllDescription(): List<Description>

    @Query("SELECT * FROM description WHERE id = :id")
    fun getDescriptionById(id: Int): Description
}