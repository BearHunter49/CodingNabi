package com.example.codingnabi.blockcoding

import android.app.Application
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.codingnabi.R
import com.example.codingnabi.data.entity.Description
import com.example.codingnabi.data.entity.Problem
import com.example.codingnabi.data.entity.Video
import com.example.codingnabi.data.repository.DescriptionRepository
import com.example.codingnabi.data.repository.ProblemRepository
import com.example.codingnabi.data.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CodingDetailViewModel(application: Application) : AndroidViewModel(application) {
    // LiveData
    private val _purpose = MutableLiveData<String>()
    val purpose: LiveData<String> = _purpose

    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String> = _hint

    private val _usableBlocks = MutableLiveData<List<String>>()
    val usableBlocks: LiveData<List<String>> = _usableBlocks

    private val _codingBlocks = MutableLiveData<MutableList<String>>()
    val codingBlocks: LiveData<MutableList<String>> = _codingBlocks

    // Repository
    private val problemRepository: ProblemRepository by lazy {
        ProblemRepository(application)
    }
    private val descriptionRepository: DescriptionRepository by lazy {
        DescriptionRepository(application)
    }
    private val videoRepository: VideoRepository by lazy {
        VideoRepository(application)
    }

    // Progress bar
    private val _isDrawing = MutableLiveData(true)
    val isDrawing: LiveData<Boolean> = _isDrawing
    private var _isFirst = true

    // Video Url
    private val _videos = hashMapOf<String, String>()


    fun initData(category: String, level: Int){
        if (_isFirst){
            viewModelScope.launch {
                loadData(category, level)
            }
        }
    }

    private suspend fun loadData(category: String, level: Int) {
        Timber.i("loadData called: $category, $level")
        withContext(Dispatchers.IO){
            val problem: Problem = problemRepository.getProblemByCategoryAndLevel(category, level)
            Timber.d("$problem")
            val description: Description = descriptionRepository.getDescriptionById(problem.descriptionId)
            val video: Video = videoRepository.getVideoById(problem.videoId)

            withContext(Dispatchers.Main){
                _videos["top"] = video.topViewUrl
                _videos["side"] = video.sideViewUrl
                _purpose.value = description.goal
                _hint.value = description.hint
                _usableBlocks.value = problem.usableBlocks.split(",")
            }
        }
        _isDrawing.value = false
    }

    fun seeVideoOfProblem(view: View){
        val bundle = bundleOf("top" to _videos["top"], "side" to _videos["side"])
        view.findNavController().navigate(R.id.action_codingDetailFragment_to_codingVideoFragment, bundle)
    }

    fun onStart(){
        if (!_isFirst) _isDrawing.value = false
    }

    fun onDestroyView(){
        _isDrawing.value = true
    }

}