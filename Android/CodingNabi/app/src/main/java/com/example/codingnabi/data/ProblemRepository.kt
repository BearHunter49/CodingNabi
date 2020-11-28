package com.example.codingnabi.data

class ProblemRepository(private val problemDAO: ProblemDAO) {
    suspend fun getAllProblem() = problemDAO.getAllProblem()
    suspend fun getProblemsByCategory(category: String) = problemDAO.getProblemsByCategory(category)
    suspend fun getProblemsByLevel(level: Int) = problemDAO.getProblemsByLevel(level)
    suspend fun getProblemByCategoryAndLevel(category: String, level: Int) =
        problemDAO.getProblemByCategoryAndLevel(category, level)

    suspend fun getLevelsByCategory(category: String) = problemDAO.getLevelsByCategory(category)
}