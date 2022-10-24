package com.example.hearthstone.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.databinding.CardByClassItemBinding

class HearthStoneAdapterSP(private var cards: List<HSCardsByClassModel>) :
    RecyclerView.Adapter<HearthStoneAdapterSP.HearthstoneViewHolder>() {
    inner class HearthstoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CardByClassItemBinding.bind(view)
        var context = view.context

        fun bindCardByClass(card: HSCardsByClassModel) {
            binding.cardModel = card
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HearthstoneViewHolder {
        return HearthstoneViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_by_class_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HearthstoneViewHolder, position: Int) {
        holder.bindCardByClass(cards[position])
    }

    override fun getItemCount(): Int = cards.size
}
