package com.example.manifesto.viewmodels

import android.app.Person
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifesto.database.config.FormApp.Companion.DB3
import com.example.manifesto.database.models.FormEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel:ViewModel() {
    val id = MutableLiveData<Long>()
    val name = MutableLiveData<String>()
    val phoneNum = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val emergencyNum = MutableLiveData<String>()
    val emergencyName = MutableLiveData<String>()
    val opSuccessful = MutableLiveData<Boolean>()

    fun saveUser(){
        var obFormEntity =  FormEntity(0, name.value!!, phoneNum.value!!, email.value!!, emergencyNum.value!!, emergencyName.value!!)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
               DB3.formDao().insertPerson(arrayListOf<FormEntity>(
                   obFormEntity
               ))
            }
        }
    }

    fun updateUser(){
    }

    fun loadData(person: FormEntity) {
        viewModelScope.launch {
                name.value = person.fullName
                phoneNum.value = person.phoneNumber
                email.value = person.email
                emergencyNum.value = person.emergencyNumber
                emergencyName.value = person.emergencyName

        }
    }
}