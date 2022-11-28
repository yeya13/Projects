package com.example.manifesto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manifesto.R
import com.example.manifesto.adapter.FormAdapter
import com.example.manifesto.databinding.FragmentMainScreenBinding
import com.example.manifesto.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel: MainScreenViewModel by viewModels()
    private lateinit var adapter: FormAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen,container,false)

        //Button to go SignInFragment
        binding.btnSignIn.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_mainScreenFragment_to_signInFragment)
        }
        //This is just a test to see the records in the database
        viewModel.testDB()

        //RecyclerView
        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)

        }

        viewModel.personalList.observe(viewLifecycleOwner, Observer {
            adapter = FormAdapter(it, viewModel)
            binding.myRecyclerView.adapter = adapter

            //Condition to show if the recyclerView is empty
            if(binding.myRecyclerView.adapter?.itemCount== 0){
                binding.warning.visibility = View.VISIBLE
                binding.warningWithRV.visibility = View.GONE
                binding.myRecyclerView.visibility = View.GONE
            }else{
                binding.warning.visibility = View.GONE
                binding.warningWithRV.visibility = View.VISIBLE
                binding.myRecyclerView.visibility = View.VISIBLE
            }
        })

        return binding.root
    }
}
