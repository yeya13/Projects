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
        binding.frag = this
        viewModel.getAllCards()

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
                        "${getString(R.string.effect_text)} $text",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                type = it.type?.let { type ->
                    HtmlCompat.fromHtml(
                        "${getString(R.string.type_text)} $type",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                rarity = it.rarity?.let { rarity ->
                    HtmlCompat.fromHtml(
                        "${getString(R.string.rarity_text)} $rarity",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                },
                cardSet = it.cardSet?.let { set ->
                    HtmlCompat.fromHtml(
                        "${getString(R.string.set_text)} $set",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                }
            )
            binding.cardModel = card

        }
        args.hsCard?.let { viewModel.queryCard(it) }
    }

    fun goSearchPage(v: View) {
        v.findNavController().popBackStack()
    }

    fun checkFavorite() {
        if (binding.icon.isChecked) {
            viewModel.insertCard()
            Toast.makeText(context, getString(R.string.added_to_list), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.deleteUser()
            Toast.makeText(context, getString(R.string.removed_to_list), Toast.LENGTH_SHORT).show()
        }
    }
}
