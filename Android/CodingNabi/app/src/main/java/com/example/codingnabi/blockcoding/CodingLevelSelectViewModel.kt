package com.example.codingnabi.blockcoding

import android.app.Application
import androidx.lifecycle.*
import com.example.codingnabi.data.Problem
import com.example.codingnabi.data.ProblemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CodingLevelSelectViewModel(application: Application) : AndroidViewModel(application) {
    private val _basicProblems = MutableLiveData<List<Problem>>()
    val basicProblem: LiveData<List<Problem>> = _basicProblems

    private val _advanceProblems = MutableLiveData<List<Problem>>()
    val advanceProblems: LiveData<List<Problem>> = _advanceProblems

    private val _loopProblems = MutableLiveData<List<Problem>>()
    val loopProblems: LiveData<List<Problem>> = _loopProblems

    private val _functionProblems = MutableLiveData<List<Problem>>()
    val functionProblems: LiveData<List<Problem>> = _functionProblems

    private val problemRepository: ProblemRepository by lazy {
        ProblemRepository(application)
    }

    init {
        viewModelScope.launch {
            getProblems()
        }
    }

    private suspend fun getProblems() {
        withContext(Dispatchers.IO){
            val test = problemRepository.getAllProblem()

            withContext(Dispatchers.Main){
                _basicProblems.value = test
            }

        }
    }
}