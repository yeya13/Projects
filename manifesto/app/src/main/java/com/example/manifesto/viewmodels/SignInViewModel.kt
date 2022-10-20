package com.example.manifesto.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifesto.database.config.FormApp.Companion.DB3
import com.example.manifesto.database.models.FormEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel:ViewModel() {
    val id= MutableLiveData<Long>(0)
    val name = MutableLiveData<String>()
    val phoneNum = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val emergencyNum = MutableLiveData<String>()
    val emergencyName = MutableLiveData<String>()
    val opSuccessful = MutableLiveData<Boolean>()

    fun saveUser(){
        val obFormEntity = getPersonData()
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO){
                    DB3.formDao().insertPerson(arrayListOf<FormEntity>(
                        obFormEntity
                    ))
                }
            }
    }

    fun getPersonData(): FormEntity {
        return FormEntity(
            id.value ?: 0,
            name.value ?: "",
            phoneNum.value ?: "",
            email.value ?: "",
            emergencyNum.value ?: "",
            emergencyName.value ?: ""
        )
    }

    fun updateUser(){
        val obFormEntity = getPersonData()
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    DB3.formDao().updatePerson(obFormEntity)
                }
                opSuccessful.value = (result>0)
        }
    }


    fun loadData(person: FormEntity) {
        viewModelScope.launch {
                id.value = person.id
                name.value = person.fullName
                phoneNum.value = person.phoneNumber
                email.value = person.email
                emergencyNum.value = person.emergencyNumber
                emergencyName.value = person.emergencyName

        }
    }

    /*fun deleteUser() {
        val obFormEntity = getPersonData()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                DB3.formDao().deletePerson(obFormEntity)
            }
            opSuccessful.value = (result>0)
        }
    }*/
}