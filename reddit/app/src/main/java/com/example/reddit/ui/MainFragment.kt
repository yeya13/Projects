package com.example.reddit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.reddit.R
import com.example.reddit.adapter.RedditAdapter
import com.example.reddit.databinding.FragmentMainBinding
import com.example.reddit.dialogues.ErrorDialog
import com.example.reddit.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.author.observe(viewLifecycleOwner){ list ->
            list?.forEach {
                it.data.author = "Author: ${it.data.author}"
                it.data.num_comments = "${it.data.num_comments} Comments"
            }
            binding.myRV.adapter = list?.let { it -> RedditAdapter(it) }
        }

        viewModel.errorDialog.observe(viewLifecycleOwner){
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            ErrorDialog().show(fragmentManager, ErrorDialog::class.java.name)
        }
    }
}
