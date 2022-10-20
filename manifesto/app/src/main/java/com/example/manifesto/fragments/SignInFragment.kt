package com.example.manifesto.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.manifesto.R
import com.example.manifesto.databinding.FragmentSignInBinding
import com.example.manifesto.dialogues.DeleteDialog
import com.example.manifesto.viewmodels.SignInViewModel


class SignInFragment : Fragment(){
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel
    val args: SignInFragmentArgs? by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in,container,false)
        viewModel = ViewModelProvider(this).get()
        binding.model = viewModel
        binding.lifecycleOwner


        //This function checks if there is something in arguments(id from person)
        Log.i("angie", "${args?.formPerson}")
        args?.formPerson?.let { person ->
            binding.btnSignSave.visibility = View.GONE
            viewModel.loadData(person)
            binding.btnSave.visibility = View.VISIBLE
        }?: receiveOnClickedUser()

        //test function update
        viewModel.opSuccessful.observe(viewLifecycleOwner, Observer{
            if(it){
                showMessage("Succesful Update")
                findNavController().navigate(R.id.action_signInFragment_to_mainScreenFragment)
            }else{
                showMessage("Error Update")
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSaveSign()
        goMainScreen()
    }

    private fun goMainScreen() {
        binding.btnBackMainScreen.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.action_signInFragment_to_mainScreenFragment)
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(context,s, Toast.LENGTH_LONG).show()
    }

    //function that validates if sendData is true, then return to the MainScreen
    private fun buttonSaveSign(){
        binding.btnSignSave.setOnClickListener {
            if (sendData()){
                it.findNavController().navigate(R.id.action_signInFragment_to_mainScreenFragment)
            }
        }
    }

    //function to validate form fields
    private fun validateForm():Boolean {
        var isValid = true
        val email=binding.edtEmail.text.toString()
        with(binding){
            //Validate Full Name
            if(edtFullName.length() < 2 || (edtFullName.length() > 12)){
                isValid = false
                txtFullName.error = "Must be 2-12 character's long."
            }else{
                txtFullName.error=null
            }

            //Validate Phone Number
            if(edtPhoneNumber.length() < 10 || (edtPhoneNumber.length() > 10)){
                isValid = false
                txtPhoneNumber.error = "Must enter 10 digits number."
            }else{
                txtPhoneNumber.error=null
            }

            //Validate Email
            if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                isValid = false
                txtEmail.error = "We do not recognize that as an email. Try again."
            }else{
                txtEmail.error=null
            }

            //Validate Emergency Number
            if(edtEmergencyNumber.length() < 10 ||(edtEmergencyNumber.length() > 10)){
                isValid = false
                txtEmergencyNumber.error = "Must enter 10 digits number."
            }else{
                txtEmergencyNumber.error=null
            }

            //Validate Emergency Name

            if(edtEmergencyName.length() < 2 || (edtEmergencyName.length() > 12)){
                isValid = false
                txtEmergencyName.error = "Must be 2-12 character's long."
            }else{
                txtEmergencyName.error=null
            }
        }
        return isValid
    }

    //Insert data
    private fun sendData():Boolean {
        var isValidSendData = true
        if(validateForm()){
            viewModel.saveUser()
            //this message is just a test. Will be deleted
            showMessage("Successful registration")

        }else{
            isValidSendData=false
            //this message is just a test. Will be deleted
            showMessage("Error")
        }
        return isValidSendData
    }

    private fun receiveOnClickedUser(){
        binding.btnSignSave.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
    }

}