package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.codingnabi.R
import com.example.codingnabi.blockcoding.viewmodel.CodingLevelSelectViewModel
import com.example.codingnabi.databinding.FragmentCodingCorrectBinding

class CodingCorrectFragment : Fragment() {
    private val viewModel: CodingLevelSelectViewModel by navGraphViewModels(R.id.navigation_coding)
    private lateinit var binding: FragmentCodingCorrectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodingCorrectBinding.inflate(inflater)

        val category = arguments?.getString("category")
        val level = arguments?.getInt("level")

        binding.textView.text = getString(R.string.cleared_message, category, level)

        category?.let {c ->
            level?.let { l ->
                viewModel.updateProblemCleared(c, l)
            }
        }

        return binding.root
    }

}