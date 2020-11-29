package com.example.codingnabi.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProblemDAO {
    @Query("SELECT * FROM problem")
    fun getAllProblem(): Array<Problem>

    @Query("SELECT * FROM problem WHERE category = :category")
    fun getProblemsByCategory(category: String): Array<Problem>

    @Query("SELECT * FROM problem WHERE category = :category and level = :level")
    fun getProblemByCategoryAndLevel(category: String, level: Int): Problem
}