package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.codingnabi.R
import com.example.codingnabi.databinding.FragmentCodingDetailBinding
import com.example.codingnabi.utils.BlockFactory
import com.example.codingnabi.utils.CodingLayoutDragListener
import com.example.codingnabi.utils.DeleteImageDragListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class CodingDetailFragment : Fragment() {
    private lateinit var binding: FragmentCodingDetailBinding
    private val viewModel: CodingDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodingDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initData()
        subscribeUI()
        setDragListener()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.usableBlocks.observe(viewLifecycleOwner) {
            Timber.i("usableBlocks observed")
            if (viewModel.isFirst) {
                Timber.i("usableBlocks drawing...")
                for (block in it) {
                    binding.blockContentLayout.addView(
                        BlockFactory.getBlockView(
                            requireContext(),
                            block
                        )
                    )
                }
            }
        }

        viewModel.codingBlocks.observe(viewLifecycleOwner) {
            Timber.i("codingBlocks observed")
//            if (viewModel.canDrawCodingBlock){
//
//            }
        }
    }

    private fun setDragListener() {
        binding.apply {
            codingContentLayout.setOnDragListener(CodingLayoutDragListener(requireContext()))
            imageDelete.setOnDragListener(DeleteImageDragListener(requireContext()))
        }
    }


    private fun initData() {
        Timber.i("initData called")
        val category = arguments?.getString("category")
        val level = arguments?.getInt("level")

        category?.let {
            level?.let {
                viewModel.initData(category, level)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<BottomNavigationView>(R.id.home_bottom_navigation)?.visibility =
            View.GONE
        viewModel.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }

}