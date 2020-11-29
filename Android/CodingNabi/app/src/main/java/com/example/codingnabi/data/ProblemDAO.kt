package com.example.codingnabi.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProblemDAO {
    @Query("SELECT * FROM problem")
    fun getAllProblem(): List<Problem>

    @Query("SELECT * FROM problem WHERE category = :category")
    fun getProblemsByCategory(category: String): List<Problem>

    @Query("SELECT * FROM problem WHERE category = :category and level = :level")
    fun getProblemByCategoryAndLevel(category: String, level: Int): Problem

    @Update
    fun updateProblemCleared(vararg problem: Problem)
}