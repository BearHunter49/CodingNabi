package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.example.codingnabi.R
import com.example.codingnabi.adapter.ProblemAdapter
import com.example.codingnabi.blockcoding.viewmodel.CodingLevelSelectViewModel
import com.example.codingnabi.databinding.FragmentCodingLevelSelectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class CodingLevelSelectFragment : Fragment() {
    private lateinit var binding: FragmentCodingLevelSelectBinding
    private val viewModel: CodingLevelSelectViewModel by navGraphViewModels(R.id.navigation_coding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_coding_level_select,
            container,
            false
        )
        binding.lifecycleOwner = this@CodingLevelSelectFragment
        binding.viewModel = viewModel

        setAdapter()

        return binding.root
    }

    private fun setAdapter() {
        binding.apply {
            recyclerviewBasic.adapter = ProblemAdapter()
            recyclerviewAdvance.adapter = ProblemAdapter()
            recyclerviewLoop.adapter = ProblemAdapter()
            recyclerviewFunction.adapter = ProblemAdapter()
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<BottomNavigationView>(R.id.home_bottom_navigation)?.visibility =
            View.VISIBLE
        viewModel.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }

}