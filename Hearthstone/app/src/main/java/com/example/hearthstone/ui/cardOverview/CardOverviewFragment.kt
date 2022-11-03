package com.example.hearthstone.ui.cardOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.FragmentCardOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardOverviewFragment() : Fragment() {
    private lateinit var binding: FragmentCardOverviewBinding

    private val viewModel: CardOverviewViewModel by viewModels()
    private val args: CardOverviewFragmentArgs by navArgs()
    private lateinit var card: HSCardsByClassModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_card_overview, container, false)
        viewModel.getAllCards()
        checkFavorite()
        goSearchPage()

        viewModel.fav.observe(viewLifecycleOwner) {
            binding.icon.isChecked = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.hsCard?.let {
            card = it.copy(
                text = it.text?.let { text ->
                    HtmlCompat.fromHtml(
                        "<b>Effect: </b>$text",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                type = it.type?.let { type ->
                    HtmlCompat.fromHtml(
                        "<b>Type: </b>$type",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                rarity = it.rarity?.let { rarity ->
                    HtmlCompat.fromHtml(
                        "<b>Rarity: </b>$rarity",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                cardSet = it.cardSet?.let { set ->
                    HtmlCompat.fromHtml(
                        "<b>Set: </b>$set",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                }
            )
            binding.cardModel = card

        }
        args.hsCard?.let { viewModel.queryCard(it) }
    }

    private fun goSearchPage() {
        binding.backToSearchPage.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_cardOverviewFragment_to_homeFragment)
        }
    }

    private fun checkFavorite() {
        binding.icon.setOnClickListener {
            if (binding.icon.isChecked) {
                viewModel.insertCard()
                Toast.makeText(context, "Added to favorite list", Toast.LENGTH_LONG).show()
            } else {
                viewModel.deleteUser()
                Toast.makeText(context, "Removed to favorite list", Toast.LENGTH_LONG).show()
            }
        }
    }
}
