package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codingnabi.R
import com.example.codingnabi.databinding.FragmentCodingDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CodingDetailFragment : Fragment() {
    private lateinit var binding: FragmentCodingDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodingDetailBinding.inflate(inflater)

        activity?.findViewById<BottomNavigationView>(R.id.home_bottom_navigation)?.visibility =
            View.GONE

        binding.apply {
            textCategory.text = arguments?.getString("category")
            textLevel.text = arguments?.getInt("level").toString()
        }

        return binding.root
    }

}