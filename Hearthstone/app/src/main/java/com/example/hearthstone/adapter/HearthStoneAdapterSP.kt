package com.example.hearthstone.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.CardByClassItemBinding
import com.example.hearthstone.ui.cardOverview.CardOverviewViewModel
import com.example.hearthstone.ui.searchpage.SearchPageFragmentDirections

class HearthStoneAdapterSP(
    private var cards: List<HSCardsByClassModel>,
    private var cardsID: List<String>? = null,
    var insertFunction: (HSCardsByClassModel) -> Unit,
    var removeFunction: (HSCardsByClassModel) -> Unit
) :
    RecyclerView.Adapter<HearthStoneAdapterSP.HearthstoneViewHolder>() {

    inner class HearthstoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CardByClassItemBinding.bind(view)
        var context = view.context


        fun bindCardByClass(card: HSCardsByClassModel) {
            binding.cardModel = card

            binding.cardViewByClass.setOnClickListener { v: View ->
                val action =
                    SearchPageFragmentDirections.actionSearchPageFragmentToCardOverviewFragment(card)
                v.findNavController().navigate(action)
            }

            cardsID?.let { list ->
                binding.icon.isChecked = list.contains(card.cardId)
            }

            binding.icon.setOnClickListener {
                if (binding.icon.isChecked) {
                    insertFunction.invoke(card)
                    Toast.makeText(context, "Added to favorite list", Toast.LENGTH_LONG).show()
                } else {
                    removeFunction.invoke(card)
                    Toast.makeText(context, "Removed to favorite list", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HearthstoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_by_class_item, parent, false)
        return HearthstoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: HearthstoneViewHolder, position: Int) {
        holder.bindCardByClass(cards[position])
    }

    override fun getItemCount(): Int = cards.size
}
