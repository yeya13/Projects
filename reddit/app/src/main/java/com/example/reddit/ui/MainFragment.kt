package com.example.reddit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.reddit.MainActivity
import com.example.reddit.R
import com.example.reddit.adapter.RedditAdapter
import com.example.reddit.data.network.repo.RedditRepo
import com.example.reddit.databinding.FragmentMainBinding
import com.example.reddit.viewmodel.MainViewModel


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = MainViewModel(MainActivity.ourApplication, RedditRepo.provideRedditRepoApi(), view)

        viewModel.author.observe(viewLifecycleOwner){ list ->
            list?.forEach {
                it.data.author = "Author: ${it.data.author}"
                it.data.num_comments = "${it.data.num_comments} Comments"
            }
            binding.myRV.adapter = list?.let { it -> RedditAdapter(it) }

            //Log.d("Clau", "$list")
        }
    }
}