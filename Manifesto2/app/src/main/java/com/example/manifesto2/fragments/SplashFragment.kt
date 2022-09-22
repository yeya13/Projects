package com.example.manifesto2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.manifesto2.R

@Suppress("DEPRECATION")
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        //SplashScreen
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mainScreenFragment)
        }, 2000)
        return view



    }


}