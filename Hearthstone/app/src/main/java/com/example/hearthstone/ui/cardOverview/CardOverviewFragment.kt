package com.example.hearthstone.ui.cardOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hearthstone.R
import com.example.hearthstone.databinding.FragmentCardOverviewBinding


class CardOverviewFragment : Fragment() {
    private lateinit var binding: FragmentCardOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_overview, container, false)
        return binding.root
    }

}
