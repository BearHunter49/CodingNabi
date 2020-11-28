package com.example.codingnabi.data

object RepositoryFactory {
    private var descriptionRepository: DescriptionRepository? = null
    private var videoRepository: VideoRepository? = null
    private var problemRepository: ProblemRepository? = null

    fun create(appDatabase: AppDatabase){
        descriptionRepository = DescriptionRepository(appDatabase.descriptionDAO())
        videoRepository = VideoRepository(appDatabase.videoDAO())
        problemRepository = ProblemRepository(appDatabase.problemDAO())
    }

    fun getDescriptionRepository() = descriptionRepository
    fun getVideoRepository() = videoRepository
    fun getProblemRepository() = problemRepository
}