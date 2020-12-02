package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.codingnabi.R
import com.example.codingnabi.blockcoding.viewmodel.CodingDetailViewModel
import com.example.codingnabi.blockcoding.viewmodel.CodingDetailViewModelFactory
import com.example.codingnabi.databinding.FragmentCodingDetailBinding
import com.example.codingnabi.utils.CodingBlockUtils
import com.example.codingnabi.utils.CodingLayoutDragListener
import com.example.codingnabi.utils.DeleteImageDragListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class CodingDetailFragment : Fragment() {
    private lateinit var binding: FragmentCodingDetailBinding
    private lateinit var viewModel: CodingDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodingDetailBinding.inflate(inflater)

        val category = arguments?.getString("category")
        val level = arguments?.getInt("level")

        // Construct ViewModel
        category?.let {
            level?.let {
                viewModel = ViewModelProvider(
                    this,
                    CodingDetailViewModelFactory(requireActivity().application, category, level)
                ).get(CodingDetailViewModel::class.java)
            }
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        subscribeUI()
        setDragListener()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.usableBlocks.observe(viewLifecycleOwner) {
            Timber.i("usableBlocks observed")

            for (block in it) {
                binding.blockContentLayout.addView(
                    CodingBlockUtils.getBlock(
                        requireContext(),
                        block
                    )
                )
            }

        }

        viewModel.codingBlocks.observe(viewLifecycleOwner) {
            Timber.i("codingBlocks observed")

        }
    }

    private fun setDragListener() {
        binding.apply {
            codingContentLayout.setOnDragListener(
                CodingLayoutDragListener(
                    requireContext(),
                    viewModel
                )
            )
            imageDelete.setOnDragListener(DeleteImageDragListener(requireContext()))
        }
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