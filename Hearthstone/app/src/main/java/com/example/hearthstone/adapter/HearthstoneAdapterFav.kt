package com.example.hearthstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstone.R
import com.example.hearthstone.database.model.HearthstoneEntity
import com.example.hearthstone.databinding.CardFavoriteItemBinding
import com.example.hearthstone.ui.favoritepage.FavoritesViewModel

class HearthstoneAdapterFav(
    private var card: List<HearthstoneEntity>,
    val viewModel: FavoritesViewModel
) : RecyclerView.Adapter<HearthstoneAdapterFav.HearthstoneViewHolder>() {

    inner class HearthstoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CardFavoriteItemBinding.bind(view)
        var context = view.context

        fun linkItem(card: HearthstoneEntity) {
            binding.carddModel = card
            binding.viewModel = viewModel
            binding.icon.isChecked = true
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HearthstoneAdapterFav.HearthstoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_favorite_item, parent, false)
        return HearthstoneViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HearthstoneAdapterFav.HearthstoneViewHolder,
        position: Int
    ) {
        holder.linkItem(card[position])
    }

    override fun getItemCount(): Int = card.size
}
