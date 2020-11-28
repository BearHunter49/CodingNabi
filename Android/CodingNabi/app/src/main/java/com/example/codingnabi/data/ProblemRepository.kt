package com.example.codingnabi.data

class ProblemRepository(private val problemDAO: ProblemDAO) {
    fun getAllProblem() = problemDAO.getAllProblem()
    fun getProblemsByCategory(category: String) = problemDAO.getProblemsByCategory(category)
    fun getProblemsByLevel(level: Int) = problemDAO.getProblemsByLevel(level)
    fun getProblemByCategoryAndLevel(category: String, level: Int) =
        problemDAO.getProblemByCategoryAndLevel(category, level)

    fun getLevelsByCategory(category: String) = problemDAO.getLevelsByCategory(category)
}