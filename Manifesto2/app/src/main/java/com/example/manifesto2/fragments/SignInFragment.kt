package com.example.manifesto2.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.manifesto2.R
import com.example.manifesto2.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in,container,false)

        //Button back Main Screen
        binding.btnBackMainScreen.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_signInFragment_to_mainScreenFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSaveSign()
    }

    private fun buttonSaveSign(){
        binding.btnSignSave.setOnClickListener { sendData() }
    }

    private fun sendData() {
        if(validateForm()){
            val dataStr = "Name: ${binding.edtFullName.text.toString()},"
            Log.i("data sent", dataStr)
        }
    }

    private fun validateForm():Boolean {
        var isValid = true
        val email=binding.edtEmail.text.toString()
        with(binding){
            //Validar Full Name
            if(edtFullName.length() < 2 || (edtFullName.length() > 12)){
                isValid = false
                txtFullName.error = "Must be 2-12 character's long."
            }else{
                txtFullName.error=null
            }

            //Validar Phone Number
            if(edtPhoneNumber.length() < 10 || (edtPhoneNumber.length() > 10)){
                isValid = false
                txtPhoneNumber.error = "Must enter 10 digits number."
            }else{
                txtPhoneNumber.error=null
            }

            //Validar Email
            if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                isValid = false
                txtEmail.error = "We do not recognize that as an email. Try again."
            }else{
                txtEmail.error=null
            }

            //Validar Emergency Number
            if(edtEmergencyNumber.length() < 10 ||(edtEmergencyNumber.length() > 10)){
                isValid = false
                txtEmergencyNumber.error = "Must enter 10 digits number."
            }else{
                txtEmergencyNumber.error=null
            }

            //Validar Emergency Name

            if(edtEmergencyName.length() < 2 || (edtEmergencyName.length() > 12)){
                isValid = false
                txtEmergencyName.error = "Must be 2-12 character's long."
            }else{
                txtEmergencyName.error=null
            }
        }
        return isValid
    }

}