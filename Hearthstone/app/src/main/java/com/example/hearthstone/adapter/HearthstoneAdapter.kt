package com.example.hearthstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstone.R
import com.example.hearthstone.databinding.CardItemBinding
import com.example.hearthstone.ui.homepage.HomeFragmentDirections

class HearthstoneAdapter(private var cards: List<String>) :
    RecyclerView.Adapter<HearthstoneAdapter.HearthstoneViewHolder>(){

    inner class HearthstoneViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var binding = CardItemBinding.bind(view)

        fun bindCard(card: String){
            when(card){
                "Mage" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_9)
                }
                "Druid" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_10)
                }
                "Hunter" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_11)
                }
                "Priest" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_12)
                }
                "Rogue" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_16)
                }
                "Paladin" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_15)
                }
                "Shaman" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_14)
                }
                "Warlock" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_13)
                }
                "Warrior" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_17)
                }
                "Demon Hunter" -> {
                    binding.txt.text = card
                    binding.cardImage.setImageResource(R.drawable.oval_copy_18)
                } else ->{
                binding.txt.text = card
                binding.cardImage.setImageResource(R.drawable.photo_copy_2)
                }
            }
            binding.cardImage.setOnClickListener{v: View ->
                val action = HomeFragmentDirections.actionHomeFragmentToSearchPageFragment(card)
                v.findNavController().navigate(action)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HearthstoneViewHolder {
        return  HearthstoneViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_item, viewGroup, false)
        )
    }
    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: HearthstoneViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }
}
