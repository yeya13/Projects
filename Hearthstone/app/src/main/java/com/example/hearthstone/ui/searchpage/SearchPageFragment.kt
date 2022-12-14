package com.example.hearthstone.ui.searchpage

import android.os.Bundle
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
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthStoneAdapterSP
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.FragmentSearchPageBinding
import com.example.hearthstone.dialogue.ErrorCardName
import com.example.hearthstone.dialogue.ErrorDialog
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
        binding.frag = this
        binding.viewModel = viewModel
        viewModel.getAllCards()

        viewModel.userSearch.observe(viewLifecycleOwner){
            binding.textSearch.text = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.hsClass?.let {
            viewModel.getCardsByClass(it)
            binding.textSearch.visibility = View.GONE
            binding.searchViewSP.visibility = View.GONE
            binding.btnSearchSP.visibility = View.GONE
            binding.className.visibility = View.VISIBLE
            binding.backToHome.visibility = View.VISIBLE
            binding.className.text = it
        }

        args.hsName?.let {
            viewModel.getCardsByName(it)
            binding.textSearch.visibility = View.VISIBLE
            binding.searchViewSP.visibility = View.VISIBLE
            binding.btnSearchSP.visibility = View.VISIBLE
            binding.className.visibility = View.GONE
            binding.backToHome.visibility = View.GONE
        }

        viewModel.getAllID()

        viewModel.cards.observe(viewLifecycleOwner) { list ->
            binding.myRecyclerViewSP.adapter =
                list?.let { it ->
                    HearthStoneAdapterSP(
                        it,
                        viewModel.cardsID.value,
                        viewModel
                    )
                }
        }

        viewModel.errorDialog.observe(viewLifecycleOwner){
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            it.show(fragmentManager, ErrorDialog::class.java.name)
        }

        viewModel.cardsName.observe(viewLifecycleOwner) { list ->
            binding.myRecyclerViewSP.adapter =
                list?.let { it ->
                    HearthStoneAdapterSP(
                        it,
                        viewModel.cardsID.value,
                        viewModel
                    )
                }

            if (viewModel.cardsName.value.isNullOrEmpty()) {
                val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                ErrorCardName().show(fragmentManager, ErrorCardName::class.java.name)
            }
        }
    }

    fun goHome(v: View) {
        v.findNavController().popBackStack()
    }
}
