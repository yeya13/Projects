package com.example.manifesto.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.manifesto.R
import com.example.manifesto.database.models.FormEntity
import com.example.manifesto.databinding.ItemListBinding
import com.example.manifesto.dialogues.DeleteDialog
import com.example.manifesto.fragments.MainScreenFragmentDirections
import com.example.manifesto.viewmodels.MainScreenViewModel

class FormAdapter(var dataSet: List<FormEntity>?, val viewModel: MainScreenViewModel) :
    RecyclerView.Adapter<FormAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet?.get(position)
        item?.let {person ->
            Log.i("item", "$person")
            viewHolder.linkItem(person)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet!!.size

    inner class ViewHolder(view: View, val viewModel: MainScreenViewModel) : RecyclerView.ViewHolder(view) {
        var binding= ItemListBinding.bind(view)
        var context = view.context

        fun linkItem(form: FormEntity){

            //Link all items
            binding.tvFullName.text = form.fullName

            //Button update
            binding.btnUpdateItem.setOnClickListener{v: View ->
                val action = MainScreenFragmentDirections.actionMainScreenFragmentToSignInFragment(form)
                v.findNavController().navigate(action)
            }

            //Button delete
            binding.btnDeleteItem.setOnClickListener {
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                var position = 0
                dataSet?.let { dataset -> position = (dataset.indexOf(form)) }
                DeleteDialog(form.fullName) { viewModel.deleteUser(form) }.show(fragmentManager, DeleteDialog::class.java.name)
            }
        }
    }

}

