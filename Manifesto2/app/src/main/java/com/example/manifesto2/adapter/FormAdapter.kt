package com.example.manifesto2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manifesto2.R
import com.example.manifesto2.database.model.FormEntity
import com.example.manifesto2.databinding.ItemListBinding


class FormAdapter(private val dataSet: List<FormEntity>?) :
    RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet?.get(position)
        viewHolder.linkItem(item!!)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet!!.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding= ItemListBinding.bind(view)
        var contexto = view.context

        fun linkItem(form: FormEntity) {
            //Link all items
            binding.tvFullName.text = "${form.fullName}"

        }

    }

}
