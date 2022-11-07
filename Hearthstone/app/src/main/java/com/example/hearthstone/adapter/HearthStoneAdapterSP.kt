package com.example.hearthstone.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.CardByClassItemBinding
import com.example.hearthstone.ui.searchpage.SearchPageFragmentDirections

class HearthStoneAdapterSP(
    private var cards: List<HSCardsByClassModel>,
    private var cardsID: List<String>? = null,
    var insertFunction: (HSCardsByClassModel) -> Unit,
    var removeFunction: (HSCardsByClassModel) -> Unit
) :
    RecyclerView.Adapter<HearthStoneAdapterSP.HearthstoneViewHolder>() {
    inner class HearthstoneViewHolder(var binding: CardByClassItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindCardByClass(card: HSCardsByClassModel) {
            binding.cardModel = card
            binding.adapter = this

            cardsID?.let { list ->
                binding.icon.isChecked = list.contains(card.cardId)
            }
        }

        fun toggleHeartIcon(card: HSCardsByClassModel) {
            if (binding.icon.isChecked) {
                insertFunction.invoke(card)
            } else {
                removeFunction.invoke(card)
            }
        }

        fun goToOverview(v: View, card: HSCardsByClassModel) {
            val action =
                SearchPageFragmentDirections.actionSearchPageFragmentToCardOverviewFragment(card)
            v.findNavController().navigate(action)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HearthstoneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding: CardByClassItemBinding = CardByClassItemBinding.inflate(
            inflater, parent, false)
        return HearthstoneViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HearthstoneViewHolder, position: Int) {
        holder.bindCardByClass(cards[position])
    }

    override fun getItemCount(): Int = cards.size
}
