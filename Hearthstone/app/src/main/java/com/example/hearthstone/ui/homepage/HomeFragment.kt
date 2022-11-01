package com.example.hearthstone.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthstoneAdapter
import com.example.hearthstone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSearch()

        //This is only to open the favorite page, when I add the bottom navigation I will delete this
        binding.logo.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
        }

        viewModel.cards.observe(viewLifecycleOwner) {
            binding.myRV.adapter = it?.let { cards -> HearthstoneAdapter(cards) }
        }
    }

    private fun validateSearch(): Boolean {
        var isValid = true
        with(binding) {
            if (searchView.query.isNullOrEmpty()) {
                isValid = false
                Toast.makeText(context, "Empty field", Toast.LENGTH_LONG).show()
            }
        }
        return isValid
    }

    private fun buttonSearch() {
        binding.btnSearch.setOnClickListener { v: View ->
            if (validateSearch()) {
                val textSearch = binding.searchView.query.toString()
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSearchPageFragmentCardName(
                        textSearch
                    )
                v.findNavController().navigate(action)
            }
        }
    }
}
