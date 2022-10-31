package com.example.hearthstone.ui.favoritepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.hearthstone.R
import com.example.hearthstone.databinding.FragmentCardOverviewBinding
import com.example.hearthstone.databinding.FragmentFavoritesBinding
import com.example.hearthstone.ui.cardOverview.CardOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }


}
