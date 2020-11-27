package com.example.codingnabi.data

import androidx.room.Query

interface ProblemDAO {
    @Query("SELECT * FROM problem")
    fun getAllProblem(): Array<Problem>

    @Query("SELECT * FROM problem WHERE category = :category")
    fun getProblemsByCategory(category: String): Array<Problem>

    @Query("SELECT * FROM problem WHERE level = :level")
    fun getProblemsByLevel(level: Int): Array<Problem>

    @Query("SELECT * FROM problem WHERE category = :category and level = :level")
    fun getProblemByCategoryAndLevel(category: String, level: Int): Problem

    @Query("SELECT category, level FROM problem WHERE category = :category")
    fun getLevelsByCategory(category: String): Array<CategoryAndLevel>
}