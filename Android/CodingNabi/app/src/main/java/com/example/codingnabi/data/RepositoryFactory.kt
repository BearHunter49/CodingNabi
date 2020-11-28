package com.example.codingnabi.data

import timber.log.Timber

object RepositoryFactory {
    private var descriptionRepository: DescriptionRepository? = null
    private var videoRepository: VideoRepository? = null
    private var problemRepository: ProblemRepository? = null

    fun create(appDatabase: AppDatabase){
        Timber.i("Repository Factory create")
        descriptionRepository = DescriptionRepository(appDatabase.descriptionDAO())
        videoRepository = VideoRepository(appDatabase.videoDAO())
        problemRepository = ProblemRepository(appDatabase.problemDAO())
    }

    fun getDescriptionRepository() = descriptionRepository
    fun getVideoRepository() = videoRepository
    fun getProblemRepository() = problemRepository
}