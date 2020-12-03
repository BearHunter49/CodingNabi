package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.example.codingnabi.R
import com.example.codingnabi.blockcoding.viewmodel.CodingDetailViewModel
import com.example.codingnabi.blockcoding.viewmodel.CodingDetailViewModelFactory
import com.example.codingnabi.databinding.FragmentCodingDetailBinding
import com.example.codingnabi.utils.CodingBlockUtils
import com.example.codingnabi.utils.CodingLayoutDragListener
import com.example.codingnabi.utils.DeleteImageDragListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
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

    @ExperimentalUnsignedTypes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cannot move logic to ViewModel...
        binding.apply {
            buttonExecute.setOnClickListener {
                val viewGroup = codingContentLayout

                if (viewGroup.childCount != 0){
                    // Async
                    CoroutineScope(Dispatchers.IO).launch {
                        CodingBlockUtils.setDroneUsable()  // 시동 끄기

                        viewGroup.children.forEach { block ->
                            val tag = block.tag.toString()

                            // 색 변경
                            withContext(Dispatchers.Main){
                                block.backgroundTintList =
                                    resources.getColorStateList(R.color.on_block_execute, null)
                            }
                            CodingBlockUtils.sendDataByTag(tag)
                            delay(1000L)

                            // 색 복구
                            withContext(Dispatchers.Main){
                                block.backgroundTintList =
                                    CodingBlockUtils.getOriginalColor(requireContext(), tag)
                            }
                        }

                        CodingBlockUtils.setDroneDisable()  // 시동 켜기
                    }
                }
            }
        }


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

            it.forEach { tag ->
                binding.codingContentLayout.addView(
                    CodingBlockUtils.getBlock(
                        requireContext(),
                        tag
                    )
                )
            }
        }
    }


    private fun setDragListener() {
        binding.apply {
            codingContentLayout.setOnDragListener(CodingLayoutDragListener(requireContext()))
            imageDelete.setOnDragListener(DeleteImageDragListener(requireContext()))
        }
    }

    private fun getCodingBlockList(): MutableList<String> {
        Timber.i("getCodingBlockList() Called")
        val blockList = mutableListOf<String>()
        binding.codingContentLayout.children.forEach {
            blockList.add(it.tag.toString())
        }
        return blockList
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<BottomNavigationView>(R.id.home_bottom_navigation)?.visibility =
            View.GONE
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.setCodingBlocks(getCodingBlockList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }

}