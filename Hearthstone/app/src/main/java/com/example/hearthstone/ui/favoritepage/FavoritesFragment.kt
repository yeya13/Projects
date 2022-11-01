package com.example.hearthstone.ui.favoritepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthstoneAdapterFav
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.database.model.HearthstoneEntity
import com.example.hearthstone.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        viewModel.getAllCards()

        viewModel.cardList.observe(viewLifecycleOwner) { list ->
            binding.myRecyclerViewFav.adapter = list?.let { cards ->
                HearthstoneAdapterFav(
                    cards,
                    this::removeCard
                )
            }
        }

        binding.backToHome.setOnClickListener{v: View ->
            v.findNavController().navigate(R.id.action_favoritesFragment_to_homeFragment)
        }
        return binding.root
    }

    private fun removeCard(hsCard: HearthstoneEntity) {
        viewModel.deleteUser(hsCard)
    }
}
