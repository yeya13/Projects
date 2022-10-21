package com.example.hearthstone.ui.HomePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hearthstone.MainActivity
import com.example.hearthstone.R
import com.example.hearthstone.adapter.HearthstoneAdapter
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = HomeViewModel(MainActivity.ourApplication, HSRepo.provideHSRepoApi(), view)

        viewModel.cards.observe(viewLifecycleOwner){
            binding.myRV.adapter = it?.let { cards -> HearthstoneAdapter(cards) }
        }
    }
}
