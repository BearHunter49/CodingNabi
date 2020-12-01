package com.example.codingnabi.data.repository

import android.app.Application
import com.example.codingnabi.data.AppDatabase
import com.example.codingnabi.data.DatabaseCopier
import com.example.codingnabi.data.entity.Problem

class ProblemRepository(private val application: Application) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(application)
    }

    suspend fun getAllProblem() = database.problemDAO().getAllProblem()
    suspend fun getProblemsByCategory(category: String) =
        database.problemDAO().getProblemsByCategory(category)

    suspend fun getProblemByCategoryAndLevel(category: String, level: Int) =
        database.problemDAO().getProblemByCategoryAndLevel(category, level)

    suspend fun updateProblemCleared(problem: Problem) =
        database.problemDAO().updateProblemCleared(problem)
}