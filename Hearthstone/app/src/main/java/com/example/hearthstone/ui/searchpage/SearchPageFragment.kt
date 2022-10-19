package com.example.hearthstone.ui.searchpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hearthstone.MainActivity
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthStoneAdapterSP
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.databinding.FragmentSearchPageBinding
import com.example.hearthstone.dialogue.ErrorCardName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPageFragment : Fragment() {

    private lateinit var binding: FragmentSearchPageBinding
    private val viewModel: SearchPageViewModel by viewModels()
    private val args: SearchPageFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_page,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goHome()
        buttonSearch()
        //viewModel = SearchPageViewModel(MainActivity.ourApplication, HSRepo.provideHSRepoApi())


        Log.i("arg", "${args.hsClass}")
        args.hsClass?.let {
            viewModel.getCardsByClass(it)
            binding.textSearch.visibility = View.GONE
            binding.searchViewSP.visibility = View.GONE
            binding.btnSearchSP.visibility = View.GONE
            binding.className.visibility = View.VISIBLE
            binding.backToHome.visibility = View.VISIBLE
            binding.className.text = it
        }
        Log.i("arg2", "${args.hsName}")
        args.hsName?.let {
            viewModel.getCardsByName(it)
            binding.textSearch.visibility = View.VISIBLE
            binding.searchViewSP.visibility = View.VISIBLE
            binding.btnSearchSP.visibility = View.VISIBLE
            binding.className.visibility = View.GONE
            binding.backToHome.visibility = View.GONE
            binding.textSearch.text = "Search results for '$it'"
        }

        viewModel.cards.observe(viewLifecycleOwner) { list ->
            binding.myRecyclerViewSP.adapter = list?.let { it -> HearthStoneAdapterSP(it) }
            Log.d("Clau", "${list?.size}")
        }

        viewModel.cardsName.observe(viewLifecycleOwner) { list ->
            binding.myRecyclerViewSP.adapter = list?.let { it -> HearthStoneAdapterSP(it) }
            Log.d("Clau", "${list?.size}")

            if (viewModel.cardsName.value.isNullOrEmpty()) {
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                ErrorCardName().show(fragmentManager, ErrorCardName::class.java.name)
            }
        }
    }

    private fun goHome() {
        binding.backToHome.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_searchPageFragment_to_homeFragment)
        }
    }

    private fun validateSearch(): Boolean {
        var isValid = true
        with(binding) {
            if (searchViewSP.query.isNullOrEmpty()) {
                isValid = false
                Toast.makeText(context, "Empty field", Toast.LENGTH_LONG).show()
            }
        }
        return isValid
    }

    @SuppressLint("SetTextI18n")
    private fun buttonSearch() {
        binding.btnSearchSP.setOnClickListener { v: View ->
            if (validateSearch()) {
                val nameCard = binding.searchViewSP.query.toString()
                viewModel.getCardsByName(nameCard)
                binding.textSearch.text = "Search results for '$nameCard'"
            }
        }
    }
}
