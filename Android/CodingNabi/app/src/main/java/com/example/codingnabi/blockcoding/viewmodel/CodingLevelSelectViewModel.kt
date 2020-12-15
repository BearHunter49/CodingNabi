package com.example.codingnabi.blockcoding.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.codingnabi.data.repository.DescriptionRepository
import com.example.codingnabi.data.entity.Problem
import com.example.codingnabi.data.repository.ProblemRepository
import com.example.codingnabi.data.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CodingLevelSelectViewModel(application: Application) : AndroidViewModel(application) {
    // LiveData
    private val _basicProblems = MutableLiveData<List<Problem>>()
    val basicProblems: LiveData<List<Problem>> = _basicProblems

    private val _advanceProblems = MutableLiveData<List<Problem>>()
    val advanceProblems: LiveData<List<Problem>> = _advanceProblems

    private val _loopProblems = MutableLiveData<List<Problem>>()
    val loopProblems: LiveData<List<Problem>> = _loopProblems

    private val _functionProblems = MutableLiveData<List<Problem>>()
    val functionProblems: LiveData<List<Problem>> = _functionProblems

    // Repository
    private val problemRepository: ProblemRepository by lazy {
        ProblemRepository(application)
    }

    // Progress bar
    private val _isDrawing = MutableLiveData(true)
    val isDrawing: LiveData<Boolean> = _isDrawing

    private var _isFirst = true


    init {
        viewModelScope.launch {
            getProblems()
        }
    }

    private suspend fun getProblems() {
        Timber.i("getProblems() called")
        withContext(Dispatchers.IO){
            _advanceProblems.postValue(problemRepository.getProblemsByCategory("advance"))
            _loopProblems.postValue(problemRepository.getProblemsByCategory("loop"))
            _functionProblems.postValue(problemRepository.getProblemsByCategory("function"))
            _basicProblems.postValue(problemRepository.getProblemsByCategory("basic"))
        }
        _isDrawing.value = false
    }

    fun updateProblemCleared(category: String, level: Int){
        Timber.i("updateProblemCleared() called")
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val problem = problemRepository.getProblemByCategoryAndLevel(category, level)
                val clearedProblem = Problem(problem.category, problem.level, problem.usableBlocks, problem.descriptionId, problem.videoId, 1)
                problemRepository.updateProblemCleared(clearedProblem)
            }
            getProblems()
        }
    }

    fun onStart(){
        if (!_isFirst) _isDrawing.value = false
    }

    fun onDestroyView(){
        _isDrawing.value = true
        _isFirst = false
    }
}