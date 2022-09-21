package com.example.manifesto2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.manifesto2.R
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        return binding.root




    }
}