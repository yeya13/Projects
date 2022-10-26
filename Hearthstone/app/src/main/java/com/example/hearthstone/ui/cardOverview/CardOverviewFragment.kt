package com.example.hearthstone.ui.cardOverview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthStoneAdapterSP
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.FragmentCardOverviewBinding
import com.example.hearthstone.ui.searchpage.SearchPageFragmentArgs
import com.example.hearthstone.ui.searchpage.SearchPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CardOverviewFragment() : Fragment() {
    private lateinit var binding: FragmentCardOverviewBinding
    private val viewModel: CardOverviewViewModel by viewModels()
    private val args: CardOverviewFragmentArgs by navArgs()

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

        args.hsCard?.let {
            val hsCard = it.copy(
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
           binding.cardModel = hsCard
        }
    }
}
