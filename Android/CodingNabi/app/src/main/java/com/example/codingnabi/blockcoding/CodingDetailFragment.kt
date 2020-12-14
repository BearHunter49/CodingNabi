package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
    private lateinit var job: Job
//    private var stop = 0

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
//        getPacket()

        return binding.root
    }

    @ExperimentalUnsignedTypes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cannot move logic to ViewModel...
        binding.apply {

            // Hint 버튼
            buttonHint.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())

                builder.run {
                    setTitle("Hint")
                    setMessage(viewModel?.hint?.value)
                    create()
                    show()
                }
            }

            // Calibration
            buttonCalibration.setOnClickListener {
                startSendingDefaultPacket()
                CoroutineScope(Dispatchers.IO).launch {
                    CodingBlockUtils.setDroneRgb()
                    CodingBlockUtils.setDroneCalibration()  // 시동 켜기
                }
            }

            // Arm
            buttonArm.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    CodingBlockUtils.setDroneArm()  // 시동 켜기
                }
            }

            // Disarm
            buttonDisarm.setOnClickListener {
                stopDrone()
            }


            // 실행 버튼
            buttonExecute.setOnClickListener {
                val viewGroup = codingContentLayout

                if (viewGroup.childCount != 0 && this@CodingDetailFragment::job.isInitialized && job.isActive) {
                    // Async
                    CoroutineScope(Dispatchers.IO).launch {
                        // First Up
                        stopSendingDefaultPacket()
                        CodingBlockUtils.sendDataByTag("fu")
                        startSendingDefaultPacket()
                        delay(1000L)

                        viewGroup.children.forEach { block ->
                            val tag = block.tag.toString()

                            // 색 변경
                            withContext(Dispatchers.Main) {
                                block.backgroundTintList =
                                    resources.getColorStateList(R.color.on_block_execute, null)
                            }

                            // 패킷 전송
                            stopSendingDefaultPacket()
                            CodingBlockUtils.sendDataByTag(tag)
                            startSendingDefaultPacket()

                            // 색 복구
                            withContext(Dispatchers.Main) {
                                block.backgroundTintList =
                                    CodingBlockUtils.getOriginalColor(requireContext(), tag)
                            }
                            delay(1000L)
                        }

                        stopDrone()
                    }
                }
            }


        }
    }

//    private fun getPacket(){
//        CoroutineScope(Dispatchers.IO).launch {
//            while (stop == 0){
//                viewModel.droneRespond.postValue(CodingBlockUtils.getPacket())
//                delay(50L)
//            }
//        }
//    }

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

    @ExperimentalUnsignedTypes
    private fun startSendingDefaultPacket(){
        if (!this::job.isInitialized || (this::job.isInitialized && !job.isActive)){
            job = CoroutineScope(Dispatchers.IO).launch {
                while (true){
                    CodingBlockUtils.sendDataByTag("default")
                    delay(50L)
                }
            }
        }

    }

    @ExperimentalUnsignedTypes
    private fun stopDrone(){
        CoroutineScope(Dispatchers.IO).launch {
            CodingBlockUtils.setDroneDisarm()
        }
        stopSendingDefaultPacket()
    }

    @ExperimentalUnsignedTypes
    private fun stopSendingDefaultPacket(){
        if (this::job.isInitialized && job.isActive){
            job.cancel()
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
//        stop = 0
    }

    @ExperimentalUnsignedTypes
    override fun onDestroyView() {
        super.onDestroyView()
        stopDrone()
        viewModel.onDestroyView()
        viewModel.setCodingBlocks(getCodingBlockList())
//        stop = 1
    }

}