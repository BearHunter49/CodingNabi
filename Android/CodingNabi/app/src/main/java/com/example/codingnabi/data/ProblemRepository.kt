package com.example.codingnabi.data

import android.content.Context

class ProblemRepository(private val context: Context) {
    private val database: AppDatabase by lazy {
        DatabaseCopier.getInstance(context)
    }

    suspend fun getAllProblem() = database.problemDAO().getAllProblem()
    suspend fun getProblemsByCategory(category: String) = database.problemDAO().getProblemsByCategory(category)
    suspend fun getProblemByCategoryAndLevel(category: String, level: Int) =
        database.problemDAO().getProblemByCategoryAndLevel(category, level)
}