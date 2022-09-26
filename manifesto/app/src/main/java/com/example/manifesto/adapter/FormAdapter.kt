package com.example.manifesto.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.manifesto.R
import com.example.manifesto.database.models.FormEntity
import com.example.manifesto.databinding.ItemListBinding
import com.example.manifesto.fragments.MainScreenFragment
import com.example.manifesto.fragments.MainScreenFragmentDirections
import com.example.manifesto.fragments.SignInFragment
import com.example.manifesto.fragments.SignInFragmentDirections

class FormAdapter(private val dataSet: List<FormEntity>?) :
    RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet?.get(position)
        item?.let {person ->
            Log.i("item", "$person")
            viewHolder.linkItem(person)
        }

        /*viewHolder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?, form: FormEntity) {
                val activity = v!!.context as AppCompatActivity
                val frag = SignInFragment()
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_Container_View, frag).addToBackStack(null).commit()
                val bundle = Bundle()
                bundle.putLong("id", form.id)
            }
        })*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet!!.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding= ItemListBinding.bind(view)
        var context = view.context



        fun linkItem(form: FormEntity){
            //Link all items
            binding.tvFullName.text = form.fullName

            binding.btnUpdateItem.setOnClickListener{v: View ->
                val action = MainScreenFragmentDirections.actionMainScreenFragmentToSignInFragment(form)
                v.findNavController().navigate(action)
            }
        }
    }
}
