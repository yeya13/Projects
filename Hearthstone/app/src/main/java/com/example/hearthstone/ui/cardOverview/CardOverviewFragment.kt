package com.example.hearthstone.ui.cardOverview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthStoneAdapterSP
import com.example.hearthstone.databinding.FragmentCardOverviewBinding
import com.example.hearthstone.ui.searchpage.SearchPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CardOverviewFragment : Fragment() {
    private lateinit var binding: FragmentCardOverviewBinding
    private val viewModel: CardOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_overview, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cardName.observe(viewLifecycleOwner) { list ->
            //Here can be a Log
        }
    }

}
