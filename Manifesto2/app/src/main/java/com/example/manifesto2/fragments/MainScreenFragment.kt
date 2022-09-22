package com.example.manifesto2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.manifesto2.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manifesto2.adapter.FormAdapter
import com.example.manifesto2.databinding.FragmentMainScreenBinding
import com.example.manifesto2.viewmodel.MainScreenViewModel


class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var viewModel: MainScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen,container,false)

        //Button Sign In
        binding.btnSignIn.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_mainScreenFragment_to_signInFragment)
        }
        //Test database
        viewModel = ViewModelProvider(this).get()
        viewModel.iniciar()

        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.personalList.observe(viewLifecycleOwner, Observer {
            binding.myRecyclerView.adapter = FormAdapter(it)
        })
        return binding.root




    }
}