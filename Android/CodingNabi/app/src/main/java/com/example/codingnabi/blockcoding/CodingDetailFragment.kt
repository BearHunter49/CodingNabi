package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.net.SocketException

class CodingDetailFragment : Fragment() {
    private lateinit var binding: FragmentCodingDetailBinding
    private lateinit var viewModel: CodingDetailViewModel
    private var defaultJob: Job? = null
    private var codingJob: Job? = null
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

        // I'll move these logic to ViewModel later...
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
                    CodingBlockUtils.setDroneCalibration()
                }
            }

            // Arm
            buttonArm.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    CodingBlockUtils.setDroneArm()
                }
            }

            // Disarm
            buttonDisarm.setOnClickListener {
                stopDrone()
            }


            // 실행 버튼
            buttonExecute.setOnClickListener {
                val viewGroup = codingContentLayout

                if (viewGroup.childCount != 0) {
                    if (defaultJob == null) {  // 수평 계산 체크
                        Toast.makeText(requireContext(), "수평계산을 먼저 해주세요!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        try {
                            codingJob = CoroutineScope(Dispatchers.IO).launch {
                                takeOffDrone()  // 이륙

                                // 블록코딩 실행
                                viewGroup.children.forEach { block ->
                                    val tag = block.tag.toString()

                                    // 색 변경
                                    withContext(Dispatchers.Main) {
                                        block.backgroundTintList =
                                            resources.getColorStateList(
                                                R.color.on_block_execute,
                                                null
                                            )
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
                                    delay(2000L)  // 호버링 계산 딜레이
                                }

                                landDrone()  // 착지
                            }
                        } catch (e: SocketException){
                            Timber.e("Coding packet: $e")
                            Toast.makeText(requireContext(), "Coding 패킷 overflow", Toast.LENGTH_SHORT).show()
                            stopDrone()
                        }
                    }
                }
            }

            // 채점
            buttonTest.setOnClickListener {

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
    private suspend fun takeOffDrone() {
        stopSendingDefaultPacket()
        CodingBlockUtils.sendDataByTag("fu")
        startSendingDefaultPacket()
        delay(2000L)  // 호버링 계산 딜레이
    }

    @ExperimentalUnsignedTypes
    private suspend fun landDrone() {
        stopSendingDefaultPacket()
        CodingBlockUtils.sendDataByTag("ld")
        stopDrone()
        delay(1000L)
    }

    @ExperimentalUnsignedTypes
    private fun startSendingDefaultPacket() {
        if (defaultJob == null) {
            try {
                defaultJob = CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        CodingBlockUtils.sendDataByTag("default")
                    }
                }
            } catch (e: SocketException) {
                Timber.e("default packet: $e")
                Toast.makeText(requireContext(), "Default 패킷 overflow", Toast.LENGTH_SHORT).show()
                defaultJob?.cancel()
            }

        }
    }

    @ExperimentalUnsignedTypes
    private fun stopDrone() {
        CoroutineScope(Dispatchers.IO).launch {
            CodingBlockUtils.setDroneDisarm()
        }
        stopSendingDefaultPacket()
        codingJob?.cancel()
        codingJob = null
    }

    @ExperimentalUnsignedTypes
    private fun stopSendingDefaultPacket() {
        defaultJob?.cancel()
        defaultJob = null
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