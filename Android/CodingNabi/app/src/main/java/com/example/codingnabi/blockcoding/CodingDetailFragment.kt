package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.viewModels
import com.example.codingnabi.R
import com.example.codingnabi.databinding.FragmentCodingDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
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

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.usableBlocks.observe(viewLifecycleOwner) {
            Timber.i("usableBlocks observed")
            for (block in it) {
                binding.blockContentLayout.addView(getBlock(block))
            }
        }

        viewModel.codingBlocks.observe(viewLifecycleOwner) {
            Timber.i("codingBlocks observed")
        }
    }

    private fun getBlock(block: String): View {
        return MaterialButton(
            requireContext(),
            null,
            R.style.DetailButton_Block
        ).apply {
            when (block) {
                "u" -> {
                    text = getString(R.string.block_up)
                    setBackgroundColor(resources.getColor(R.color.block_up_down, null))
                }
                "d" -> {
                    text = getString(R.string.block_down)
                    setBackgroundColor(resources.getColor(R.color.block_up_down, null))
                }
                "l" -> {
                    text = getString(R.string.block_left)
                    setBackgroundColor(resources.getColor(R.color.block_left_right, null))
                }
                "r" -> {
                    text = getString(R.string.block_right)
                    setBackgroundColor(resources.getColor(R.color.block_left_right, null))
                }
                "f" -> {
                    text = getString(R.string.block_forward)
                    setBackgroundColor(resources.getColor(R.color.block_forward_backward, null))
                }
                "b" -> {
                    text = getString(R.string.block_backward)
                    setBackgroundColor(resources.getColor(R.color.block_forward_backward, null))
                }
                "lp" -> {
                    text = getString(R.string.block_loop)
                    setBackgroundColor(resources.getColor(R.color.block_loop, null))
                }
                "fc" -> {
                    text = getString(R.string.block_function)
                    setBackgroundColor(resources.getColor(R.color.block_function, null))
                }
                else -> {
                    Timber.e("There is no block such $block")
                    text = getString(R.string.error)
                    setBackgroundColor(resources.getColor(R.color.white, null))
                }
            }
        }
    }

    private fun initData() {
        val category = arguments?.getString("category")
        val level = arguments?.getInt("level")

        category?.let {
            level?.let {
                viewModel.initData(category, level)
            }
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