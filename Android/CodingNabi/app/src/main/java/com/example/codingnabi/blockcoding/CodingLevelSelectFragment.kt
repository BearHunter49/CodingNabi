package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.codingnabi.R
import com.example.codingnabi.data.ProblemRepository
import com.example.codingnabi.databinding.FragmentCodingLevelSelectBinding
import kotlinx.coroutines.*
import timber.log.Timber

class CodingLevelSelectFragment : Fragment() {
    private lateinit var binding: FragmentCodingLevelSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coding_level_select, container, false)

        // Test
        val problemRepository = ProblemRepository(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            val test = problemRepository.getAllProblem()
            test.forEach {
                Timber.d(it.toString())
            }

        }


        return binding.root
    }

}